//package com.jideguru.epub_viewer
//
//import android.app.Activity
//import android.content.Context
//import io.flutter.plugin.common.BinaryMessenger
//
//import io.flutter.plugin.common.MethodCall
//
//import io.flutter.plugin.common.MethodChannel
//
//import io.flutter.plugin.common.MethodChannel.MethodCallHandler
//
//import io.flutter.plugin.common.MethodChannel.Result
//
//import io.flutter.plugin.common.PluginRegistry.Registrar
//import io.flutter.embedding.engine.plugins.FlutterPlugin
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.launch
//import kotlin.coroutines.CoroutineContext
//
//
//class EpubViewerPlugin : MethodCallHandler{
//    private var activity: Activity? = null
//    private var context: Context? = null
//    var messenger: BinaryMessenger? = null
////    private var job: Job = Job()
////
////    override val coroutineContext: CoroutineContext
////        get() = Dispatchers.Main + job
//    override fun onMethodCall(call: MethodCall, result: Result) {
//        if (call.method.equals("setConfig")) {
//        } else if (call.method.equals("open")) {
//            val arguments = call.arguments as Map<String, Any>
//            val bookPath = arguments["bookPath"].toString()
//            val lastLocation = arguments["lastLocation"].toString()
//
////            launch {
////                Reader().openBook(bookPath, lastLocation)
////            }
//        } else if (call.method.equals("close")) {
////            reader.close()
//        } else {
//            result.notImplemented()
//        }
//    }
//
//    companion object {
//        private var activity: Activity? = null
//        private var context: Context? = null
//        var messenger: BinaryMessenger? = null
//        /** Plugin registration.  */
//        fun registerWith(registrar: Registrar) {
//            context = registrar.context()
//            activity = registrar.activity()
//            messenger = registrar.messenger()
//            val channel = MethodChannel(registrar.messenger(), "epub_viewer")
//            channel.setMethodCallHandler(EpubViewerPlugin())
//        }
//    }
//
//    fun registerWith(registrar: Registrar) {
//        context = registrar.context()
//        activity = registrar.activity()
//        messenger = registrar.messenger()
//        val channel = MethodChannel(registrar.messenger(), "epub_viewer")
//        channel.setMethodCallHandler(EpubViewerPlugin())
//    }
//}
