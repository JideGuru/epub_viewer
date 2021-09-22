package com.jideguru.epub_viewer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.Map;

import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import androidx.annotation.NonNull;

/** EpubReaderPlugin */
public class EpubViewerPlugin implements MethodCallHandler, FlutterPlugin, ActivityAware {

  private Reader reader;
  private ReaderConfig config;
  private MethodChannel channel;
  static private Activity activity;
  static private Context context;
  static BinaryMessenger messenger;
  static private EventChannel eventChannel;
  static private EventChannel.EventSink sink;
  private static final String channelName = "epub_viewer";

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {

    context = registrar.context();
    activity = registrar.activity();
    messenger = registrar.messenger();
    new EventChannel(messenger,"page").setStreamHandler(new EventChannel.StreamHandler() {

      @Override
      public void onListen(Object o, EventChannel.EventSink eventSink) {

        sink = eventSink;
        if(sink == null) {
          Log.i("empty", "Sink is empty");
        }
      }

      @Override
      public void onCancel(Object o) {

      }
    });


    final MethodChannel channel = new MethodChannel(registrar.messenger(), "epub_viewer");
    channel.setMethodCallHandler(new EpubViewerPlugin());

  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
    messenger = binding.getBinaryMessenger();
    context = binding.getApplicationContext();
    new EventChannel(messenger,"page").setStreamHandler(new EventChannel.StreamHandler() {

      @Override
      public void onListen(Object o, EventChannel.EventSink eventSink) {

        sink = eventSink;
        if(sink == null) {
          Log.i("empty", "Sink is empty");
        }
      }

      @Override
      public void onCancel(Object o) {

      }
    });
    channel = new MethodChannel(binding.getFlutterEngine().getDartExecutor(), channelName);
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    // TODO: your plugin is no longer attached to a Flutter experience.
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
    activity = activityPluginBinding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {

  }

  @Override
  public void onDetachedFromActivity() {
    activity = null;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {

    if (call.method.equals("setConfig")){
      Map<String,Object> arguments = (Map<String, Object>) call.arguments;
      String identifier = arguments.get("identifier").toString();
      String themeColor = arguments.get("themeColor").toString();
      String scrollDirection = arguments.get("scrollDirection").toString();
      Boolean nightMode = Boolean.parseBoolean(arguments.get("nightMode").toString());
      Boolean allowSharing = Boolean.parseBoolean(arguments.get("allowSharing").toString());
      Boolean enableTts = Boolean.parseBoolean(arguments.get("enableTts").toString());
      config = new ReaderConfig(context,identifier,themeColor,
              scrollDirection,allowSharing, enableTts,nightMode);

    } else if (call.method.equals("open")){

      Map<String,Object> arguments = (Map<String, Object>) call.arguments;
      String bookPath = arguments.get("bookPath").toString();
      String lastLocation = arguments.get("lastLocation").toString();

      Log.i("opening", "In open function");
      if(sink == null) {
        Log.i("sink status", "sink is empty");
      }
      reader = new Reader(context,messenger,config, sink);
      reader.open(bookPath, lastLocation);

    }else if(call.method.equals("close")){
      reader.close();
    }
    else if (call.method.equals("setChannel")){
      eventChannel = new EventChannel(messenger,"page");
      eventChannel.setStreamHandler(new EventChannel.StreamHandler() {

        @Override
        public void onListen(Object o, EventChannel.EventSink eventSink) {

          sink = eventSink;
        }

        @Override
        public void onCancel(Object o) {

        }
      });
    }

    else {
      result.notImplemented();
    }
  }
}
