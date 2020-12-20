package com.jideguru.epub_viewer

import android.app.Activity
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import timber.log.Timber
import kotlinx.coroutines.*
import kotlin.coroutines.*
import android.os.Looper
import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle

/** EpubViewerPlugin */
class EpubViewerPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private lateinit var context: Context
    private lateinit var activity: Activity
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private var reader: Reader? = null

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        reader = Reader()
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "epub_viewer")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method.equals("setConfig")) {
            Timber.tag("epub_viewer").i("SetConfig")
        } else if (call.method.equals("open")) {
            val arguments = call.arguments as Map<String, Any>
            val bookPath = arguments["bookPath"].toString()
            val lastLocation = arguments["lastLocation"].toString()
            Timber.tag("epub_viewer").i("Open")


            val intent = Intent(context, Reader::class.java).apply {
                putExtra("bookPath", bookPath)
            }
            activity.startActivity(intent)
            // no thread
//            mainScope.launch {
//                try {
//                    withContext(Dispatchers.Main) {
//                        reader?.openBook(bookPath, lastLocation)
//                    }
//
//                    result.success(null)
//                } catch (e: Exception) {
//                    result.error("OpenBookException", e.message, null)
//                }
//            }
        } else if (call.method.equals("close")) {
//            reader.close()
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onDetachedFromActivity() {
        channel.setMethodCallHandler(null)
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        /// TODO("Not yet implemented")
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity;
    }

    override fun onDetachedFromActivityForConfigChanges() {
        /// TODO("Not yet implemented")
    }
}
