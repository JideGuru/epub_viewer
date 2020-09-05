import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:epub_kitty/epub_kitty.dart';

void main() {
  const MethodChannel channel = MethodChannel('epub_kitty');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });
}
