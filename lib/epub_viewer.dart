import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

part 'model/enum/epub_scroll_direction.dart';

part 'model/epub_locator.dart';

part 'utils/util.dart';

class EpubViewer {
  static const MethodChannel _channel = const MethodChannel('epub_viewer');
  static const EventChannel _pageChannel = const EventChannel('page');

  /// @param identifier unique key for epub
  /// @param themeColor
  /// @param scrollDirection
  /// @param allowSharing
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

  /// @param bookPath the local path in cache
  static void open(String bookPath, {EpubLocator lastLocation}) async {
    Map<String, dynamic> agrs = {
      "bookPath": bookPath,
      'lastLocation': jsonEncode(lastLocation.toJson()),
    };
    await _channel.invokeMethod('open', agrs);
  }

  static Stream get locatorStream {
    Stream pageStream =
        _pageChannel.receiveBroadcastStream().map((value) => value);

    return pageStream;
  }
}
