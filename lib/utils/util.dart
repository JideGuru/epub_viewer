part of 'package:epub_viewer/epub_viewer.dart';

class Util {
  static String getHexFromColor(Color color) {
    return '#${color
        .toString()
        .replaceAll('Color(0xff', '')
        .replaceAll('MaterialColor(', '')
        .replaceAll('MaterialAccentColor(', '')
        .replaceAll('primary value: Color(0xff', '')
        .replaceAll('primary', '')
        .replaceAll('value:', '')
        .replaceAll(')', '')
        .trim()}';
  }
}
