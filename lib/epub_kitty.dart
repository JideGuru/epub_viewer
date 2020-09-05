import 'dart:convert';

import 'package:flutter/services.dart';

class EpubKitty {
  static const MethodChannel _channel = const MethodChannel('epub_kitty');

  /// @param identifier unique key for epub
  /// @param themeColor
  /// @param scrollDirection
  /// @param allowSharing
  static void setConfig(String identifier, String themeColor,
      String scrollDirection, bool allowSharing) async {
    Map<String, dynamic> agrs = {
      "identifier": identifier,
      "themeColor": themeColor,
      "scrollDirection": scrollDirection,
      "allowSharing": allowSharing,
    };
    await _channel.invokeMethod('setConfig', agrs);
  }

  /// @param bookPath the local path in cache
  static void open(String bookPath, {Map lastLocation}) async {
    Map<String, dynamic> agrs = {
      "bookPath": bookPath,
      'lastLocation': jsonEncode(lastLocation),
    };
    await _channel.invokeMethod('open', agrs);
  }
}
