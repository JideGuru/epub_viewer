import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

part 'model/enum/epub_scroll_direction.dart';

part 'model/epub_locator.dart';

part 'utils/util.dart';

class EpubViewer {
  static const MethodChannel _channel = const MethodChannel('epub_viewer');
  static const EventChannel _pageChannel = const EventChannel('page');

  /// Configure Viewer's with available values
  static void setConfig(String identifier, Color themeColor,
      EpubScrollDirection scrollDirection, bool allowSharing) async {
    Map<String, dynamic> agrs = {
      "identifier": identifier,
      "themeColor": Util.getHexFromColor(themeColor),
      "scrollDirection": scrollDirection == EpubScrollDirection.HORIZONTAL
          ? 'horizontal'
          : 'vertical',
      "allowSharing": allowSharing,
    };
    await _channel.invokeMethod('setConfig', agrs);
  }

  /// bookPath should be a local file.
  /// Last location is only available for android.
  static void open(String bookPath, {EpubLocator lastLocation}) async {
    Map<String, dynamic> agrs = {
      "bookPath": bookPath,
      'lastLocation': lastLocation == null
          ? ''
          : jsonEncode(lastLocation.toJson()),
    };
    await _channel.invokeMethod('open', agrs);
  }

  static Stream get locatorStream {
    Stream pageStream = _pageChannel
        .receiveBroadcastStream()
        .map((value) => Platform.isAndroid ? value : '{}');

    return pageStream;
  }
}
