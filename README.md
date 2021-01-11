# Epub Viewer [![pub package](https://img.shields.io/pub/v/epub_viewer.svg)](https://pub.dartlang.org/packages/epub_viewer)


originally a fork of [epub_kitty](https://github.com/451518849/epub_kitty) with few more features.
i made this out of epub_kitty because the author was inactive(he isn't merging PRs or attending to issues) and i started having alot of issues with the plugin

epub_viewer is an epub ebook reader that encapsulates the [folioreader](https://folioreader.github.io/FolioReaderKit/) framework.
  It supports iOS and android.

## Features
| Name | Android | iOS |
|------|-------|------|
| Reading Time Left / Pages left | ✅ | ✅ |
| Last Read Locator | ✅ | ✅ |
| Distraction Free Reading | ✅ | ❌ |
| Load ePub from Asset | ✅ | ✅ |

## ScreenShots
<a href="#screenshots">
  <img src="https://raw.githubusercontent.com/JideGuru/epub_viewer/master/screenshots/1.png" width="200px">
</a>&nbsp;&nbsp;
<a href="#screenshots">
  <img src="https://raw.githubusercontent.com/JideGuru/epub_viewer/master/screenshots/2.png" width="200px">
</a>&nbsp;&nbsp;
<a href="#screenshots">
  <img src="https://raw.githubusercontent.com/JideGuru/epub_viewer/master/screenshots/3.png" width="200px">
</a>&nbsp;&nbsp;
<a href="#screenshots">
  <img src="https://raw.githubusercontent.com/JideGuru/epub_viewer/master/screenshots/4.png" width="200px">
</a>&nbsp;&nbsp;
<a href="#screenshots">
  <img src="https://raw.githubusercontent.com/JideGuru/epub_viewer/master/screenshots/5.png" width="200px">
</a>&nbsp;&nbsp;
<a href="#screenshots">
  <img src="https://raw.githubusercontent.com/JideGuru/epub_viewer/master/screenshots/6.png" width="200px">
</a>&nbsp;&nbsp;

## Install
This plugin requires `Swift` to work on iOS.
Also, the minimum deployment target is 9.0
```
platform :ios, '9.0'
```

Import into pubspec.yaml
```
dependencies:
  epub_viewer: latest_version
```

Note: Please add this to the release build type in your app build.gradle to avoid crashes on android release builds
```
minifyEnabled false
shrinkResources false
```

## Usage
```dart
EpubViewer.setConfig(
  themeColor: Theme.of(context).primaryColor,
  identifier: "iosBook",
  scrollDirection: EpubScrollDirection.VERTICAL,
  allowSharing: true,
  enableTts: true,
)

/**
* @bookPath
* @lastLocation (optional and only android)
*/
EpubViewer.open(
  'bookPath',
  lastLocation: EpubLocator.fromJson({
    "bookId": "2239",
    "href": "/OEBPS/ch06.xhtml",
    "created": 1539934158390,
    "locations": {
       "cfi": "epubcfi(/0!/4/4[simple_book]/2/2/6)"
    }
  }), // first page will open up if the value is null
);

// Get locator which you can save in your database
EpubViewer.locatorStream.listen((locator) {
   print('LOCATOR: ${EpubLocator.fromJson(jsonDecode(locator))}');
   // convert locator from string to json and save to your database to be retrieved later
});
```

You can also load epub from your assets using `EpubViewer.openAsset()`

```dart
await EpubViewer.openAsset(
  'assets/3.epub',
  lastLocation: EpubLocator.fromJson({
      "bookId": "2239",
      "href": "/OEBPS/ch06.xhtml",
      "created": 1539934158390,
      "locations": {
         "cfi": "epubcfi(/0!/4/4[simple_book]/2/2/6)"
      }
  }), // first page will open up if the value is null
 );

// Get locator which you can save in your database
EpubViewer.locatorStream.listen((locator) {
   print('LOCATOR: ${EpubLocator.fromJson(jsonDecode(locator))}');
   // convert locator from string to json and save to your database to be retrieved later
});
 ```

Check the [Sample](https://github.com/JideGuru/epub_viewer/tree/master/example) project or [this ebook app](https://github.com/JideGuru/FlutterEbookApp) for implementation
## Issues

If you encounter any problems feel free to open an issue. If you feel the library is
missing a feature, please raise a ticket on Github and I'll look into it.
Pull request are also welcome.

For help getting started with Flutter, view the online
[documentation](https://flutter.io/).

For help on editing plugin code, view the [documentation](https://flutter.io/platform-plugins/#edit-code).
	
