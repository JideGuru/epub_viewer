part of 'package:epub_viewer/epub_viewer.dart';

class Util {
  static String getHexFromColor(Color color) {
    return '#${color
        .toString()
        .replaceAll('Color(0x', '')
        .replaceAll(')', '')
        .trim()}';
  }
}
