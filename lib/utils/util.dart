part of 'package:epub_viewer/epub_viewer.dart';

class Util {
  /// Get HEX code from [Colors], [MaterialColor],
  /// [Color] and [MaterialAccentColor]
  static String getHexFromColor(Color color) {
    return '#${color.toString().replaceAll('Color(0xff', '').replaceAll(
        'MaterialColor(', '').replaceAll('MaterialAccentColor(', '').replaceAll(
        'primary value: Color(0xff', '').replaceAll('primary', '').replaceAll(
        'value:', '').replaceAll(')', '').trim()}';
  }

  /// Convert [EpubScrollDirection] to FolioReader reader String
  static String getDirection(EpubScrollDirection direction) {
    switch (direction) {
      case EpubScrollDirection.VERTICAL:
        return 'vertical';
        break;
      case EpubScrollDirection.HORIZONTAL:
        return 'horizontal';
        break;
      case EpubScrollDirection.ALLDIRECTIONS:
        return 'alldirections';
        break;
      default:
        return 'alldirections';
        break;
    }
  }
}
