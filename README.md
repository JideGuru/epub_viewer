# epub_reader

![](https://img.shields.io/badge/build-passing-brightgreen)
![](https://img.shields.io/badge/version-0.0.1-orange)
![](https://img.shields.io/badge/platform-flutter-lightgrey)


a fork of [epub_kitty](https://github.com/451518849/epub_kitty) with few more features.
i made this out of epub_kitty because the author was inactive(he isn't merging PRs or attending to issues) and i started having alot of issues with the plugin

![](1.jpeg)
![](2.jpeg)


epub_reader is an epub ebook reader that encapsulates the [folioreader](https://folioreader.github.io/FolioReaderKit/) framework.
  It supports iOS and android, but is customizable on iOS. 
  It is very easy to use, you just need to set up the configuration file can open the ebook, very convenient.
  However, it is not yet highly customizable on android.
  But it has been able to meet daily needs.

## Install
	dependencies:
	  epub_reader: ^0.0.1

## Simple Use
   
    first step
    /**
     * @identifier (android useless)
     * @themeColor
     * @scrollDirection (android useless)
     * @allowSharing (android useless)
     */
    EpubKitty.setConfig("book", "#32a852","vertical",true);
    
    second step
	 /**
	 * @bookPath
	 * @lastLocation (optional)
	 */
	EpubReader.open(
	   'bookPath',
	   lastLocation: {
         "bookId": "2239",
         "href": "/OEBPS/ch06.xhtml",
         "created": 1539934158390,
         "locations": {
            "cfi": "epubcfi(/0!/4/4[simple_book]/2/2/6)"
         }
        },
	);
	
	third step
	// Get locator which you can save in your database
	EpubReader.locatorStream.listen((event) {
       print('Locator: $event');
    });

## Tool Language
#### iOS
plugin in ios default language is en, if you are chinese, you should configure Localizable.strings. And you can see example to match.

#### android
if you are chinese, you should use res/strings.xml to match chinese. see [issue #7](https://github.com/451518849/epub_kitty/issues/7)

## issues
if you can not install it, mybe it exists some confict in your plugins or may not full-install.

more solutions see issuses.

Good luck to you !

### Welcome TO PR
	
