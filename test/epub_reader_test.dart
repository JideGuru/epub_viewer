import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:epub_reader/epub_reader.dart';

void main() {
  const MethodChannel channel = MethodChannel('epub_reader');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });
}
