package com.jideguru.epub_viewer

import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import timber.log.Timber
import kotlinx.coroutines.*
import kotlin.coroutines.*

/** EpubViewerPlugin */
class EpubViewerPlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

    private val _mainScope = CoroutineScope(Dispatchers.Main)

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "epub_viewer")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) = runBlocking {
        if (call.method.equals("setConfig")) {
            Timber.tag("epub_viewer").i("SetConfig")
        } else if (call.method.equals("open")) {
            val arguments = call.arguments as Map<String, Any>
            val bookPath = arguments["bookPath"].toString()
            val lastLocation = arguments["lastLocation"].toString()
            Timber.tag("epub_viewer").i("Open")
            withContext(Dispatchers.IO) {
                // Run code on a IO thread (the amount of IO threads are 64 by default).
                Reader().openBook(bookPath, lastLocation)
            }
//            launch {
//                Reader().openBook(bookPath, lastLocation)
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
}
