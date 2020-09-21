part of 'package:epub_viewer/epub_viewer.dart';

class Util {
  /// Get HEX code from [Colors], [MaterialColor],
  /// [Color] and [MaterialAccentColor]
  static String getHexFromColor(Color color) {
    return '#${color.toString().replaceAll('ColorSwatch(', '').replaceAll('Color(0xff', '').replaceAll('MaterialColor(', '').replaceAll('MaterialAccentColor(', '').replaceAll('primary value: Color(0xff', '').replaceAll('primary', '').replaceAll('value:', '').replaceAll(')', '').trim()}';
  }

  /// Convert [EpubScrollDirection] to FolioReader reader String
  static String getDirection(EpubScrollDirection direction) {
    switch (direction) {
      case EpubScrollDirection.VERTICAL:
        return 'vertical';
      case EpubScrollDirection.HORIZONTAL:
        return 'horizontal';
      case EpubScrollDirection.ALLDIRECTIONS:
        return 'alldirections';
      default:
        return 'alldirections';
    }
  }

  /// Create a temporary [File] from an asset epub
  /// to be opened by [EpubViewer]
  static Future<File> getFileFromAsset(String asset) async {
    ByteData data = await rootBundle.load(asset);
    String dir = (await getTemporaryDirectory()).path;
    String path = '$dir/${basename(asset)}';
    final buffer = data.buffer;
    return File(path).writeAsBytes(
        buffer.asUint8List(data.offsetInBytes, data.lengthInBytes));
  }
}
