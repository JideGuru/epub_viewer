package com.jideguru.epub_viewer

import com.jideguru.epub_viewer.utils.extensions.blockingProgressDialog
import android.os.CountDownTimer
import android.util.Log
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.jideguru.epub_viewer.db.BooksDatabase
import com.jideguru.epub_viewer.db.books
import com.jideguru.epub_viewer.drm.DRMLibraryService
import com.jideguru.epub_viewer.library.BooksAdapter
import com.jideguru.epub_viewer.permissions.PermissionHelper
import com.jideguru.epub_viewer.permissions.Permissions
import com.jideguru.epub_viewer.utils.NavigatorContract
import com.jideguru.epub_viewer.utils.extensions.download
import kotlinx.coroutines.*
import kotlin.coroutines.*
import org.jetbrains.anko.design.longSnackbar
import org.json.JSONObject
import org.jsoup.parser.Parser
import org.readium.r2.shared.Injectable
import org.readium.r2.shared.extensions.extension
import org.readium.r2.shared.extensions.tryOrNull
import org.readium.r2.shared.format.Format
import org.readium.r2.shared.publication.ContentProtection
import org.readium.r2.shared.publication.Locator
import org.readium.r2.shared.publication.Publication
import org.readium.r2.shared.publication.services.isRestricted
import org.readium.r2.shared.publication.services.protectionError
import org.readium.r2.streamer.Streamer
import org.readium.r2.streamer.server.Server
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.net.ServerSocket
import java.net.URL
import java.util.*
import com.jideguru.epub_viewer.BuildConfig.DEBUG
import org.readium.r2.shared.extensions.putPublication
import com.jideguru.epub_viewer.epub.EpubActivity
import android.content.Intent

class Reader : AppCompatActivity() {
    private lateinit var server: Server
    private var localPort: Int = 0

    //    private lateinit var permissionHelper: PermissionHelper
//    private lateinit var permissions: Permissions
    private lateinit var preferences: SharedPreferences
    private lateinit var navigatorLauncher: ActivityResultLauncher<NavigatorContract.Input>
    protected var contentProtections: List<ContentProtection> = emptyList()
    private lateinit var streamer: Streamer
    private lateinit var R2DIRECTORY: String
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ebookPath = intent.getStringExtra("bookPath")

        preferences = getSharedPreferences("org.readium.r2.settings", Context.MODE_PRIVATE)

        streamer = Streamer(this, contentProtections = contentProtections)

        val s = ServerSocket(if (DEBUG) 8088 else 0)
        s.localPort
        s.close()

        localPort = s.localPort
        server = Server(localPort, applicationContext)

        val properties = Properties()
        val inputStream = this.assets.open("configs/config.properties")
        properties.load(inputStream)
        val useExternalFileDir = properties.getProperty("useExternalFileDir", "false")!!.toBoolean()

        R2DIRECTORY = if (useExternalFileDir) {
            this.getExternalFilesDir(null)?.path + "/"
        } else {
            this.filesDir.path + "/"
        }

        navigatorLauncher = registerForActivityResult(NavigatorContract()) { pubData: NavigatorContract.Output? ->
            if (pubData == null)
                return@registerForActivityResult

            tryOrNull { pubData.publication.close() }
            Timber.d("Publication closed")
            if (pubData.deleteOnResult)
                tryOrNull { pubData.file.file.delete() }
        }
        startServer(ebookPath!!)
//        val timer = object: CountDownTimer(20000, 1000) {
//            override fun onTick(millisUntilFinished: Long) {}
//
//            override fun onFinish() {
//                Log.i("TIMER", "LAUNCHED")
//                // use Main Dispatchers to launch the openbook function
//                mainScope.launch {openBook(ebookPath!!, "{}")}
//            }
//        }
//        timer.start()
//        mainScope.launch { openBook(ebookPath!!, "{}") }
    }

    override fun onDestroy() {
        super.onDestroy()
        //TODO not sure if this is needed
        stopServer()
    }

    private fun startServer(bookPath: String) {
        if (!server.isAlive) {
            Log.i("EPUBSCREEN", "STARTSERVER")
            try {
                server.start()
            } catch (e: IOException) {
                // do nothing
                if (DEBUG) Timber.e(e)
            }
            if (server.isAlive) {
                Log.i("EPUBSCREEN", "LOADFILES")
//                // Add your own resources here
//                server.loadCustomResource(assets.open("scripts/test.js"), "test.js")
//                server.loadCustomResource(assets.open("styles/test.css"), "test.css")
//                server.loadCustomFont(assets.open("fonts/test.otf"), applicationContext, "test.otf")

                server.loadCustomResource(assets.open("Search/mark.js"), "mark.js", Injectable.Script)
                server.loadCustomResource(assets.open("Search/search.js"), "search.js", Injectable.Script)
                server.loadCustomResource(assets.open("Search/mark.css"), "mark.css", Injectable.Style)

                isServerStarted = true
                mainScope.launch { openBook(bookPath, "{}") }
            }
        }
    }

    private fun stopServer() {
        if (server.isAlive) {
            server.stop()
            isServerStarted = false
        }
    }

    private suspend fun URL.copyToTempFile(): org.readium.r2.shared.util.File? = tryOrNull {
        val filename = UUID.randomUUID().toString()
        val file = File("$R2DIRECTORY$filename.$extension")

        if (download(file.path)) org.readium.r2.shared.util.File(file.path)
        else null
    }

    public suspend fun openBook(bookPath: String, lastLocation: String) {
        val progress = blockingProgressDialog(getString(R.string.progress_wait_while_preparing_book))
        progress.show()
        val locator = Locator.fromJSON(JSONObject(lastLocation))
        val book = File(bookPath)
        val remoteUrl = tryOrNull { URL(book.path).copyToTempFile() }
        val format = Format.of(fileExtension = book.extension.removePrefix("."))
        val file = remoteUrl // remote file
                ?: org.readium.r2.shared.util.File(book.path, format = format) // local file
        Log.i("OPENING", "FETCHED")
        streamer.open(file, allowUserInteraction = true, sender = this@Reader)
                .onFailure {
                    Log.i("OPENING", "FAILED")
                    Timber.d(it)
                    progress.dismiss()
                    this.finish()
//                        presentOpeningException(it)
                }
                .onSuccess { it ->
                    if (it.isRestricted) {
                        Log.i("OPENING", "SUCCESS RESTRICTED")
                        progress.dismiss()
                        this.finish()
                        it.protectionError.let { error ->
                            Timber.d(error)
//                                catalogView.longSnackbar(error?.getUserMessage(this@LibraryActivity))
                        }
                    } else {
                        Log.i("OPENING", "SUCCESS")
                        prepareToServe(it, file)
                        Log.i("OPENING", "PREPARED")
                        progress.dismiss()
                        // close this activity before opening the next one
//                        this.finish()
                        navigatorLauncher.launch(
                                NavigatorContract.Input(
                                        file = file,
                                        format = format,
                                        publication = it,
                                        bookId = book.lastModified(),
                                        initialLocator = locator,
                                        deleteOnResult = remoteUrl != null,
                                        baseUrl = Publication.localBaseUrlOf(file.name, localPort)
                                )
                        )
                        Log.i("OPENING", "OPENED")
                    }
                }
    }

    private fun prepareToServe(publication: Publication, file: org.readium.r2.shared.util.File) {
        val key = publication.metadata.identifier ?: publication.metadata.title
        preferences.edit().putString("$key-publicationPort", localPort.toString()).apply()
        val userProperties = applicationContext.filesDir.path + "/" + Injectable.Style.rawValue + "/UserProperties.json"
        server.addEpub(publication, null, "/${file.name}", userProperties)
    }

    companion object {

        var isServerStarted = false
            private set

    }
}