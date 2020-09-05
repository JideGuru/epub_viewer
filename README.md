# epub_kitty

![](https://img.shields.io/badge/build-passing-brightgreen)
![](https://img.shields.io/badge/version-0.1.7-orange)
![](https://img.shields.io/badge/platform-flutter-lightgrey)


![](1.jpeg)
![](2.jpeg)


epub_kitty is an epub ebook reader that encapsulates the [folioreader](https://folioreader.github.io/FolioReaderKit/) framework. 
  It supports iOS and android, but is customizable on iOS. 
  It is very easy to use, you just need to set up the configuration file can open the ebook, very convenient.
  However, it is not yet highly customizable on android.
  But it has been able to meet daily needs.

[中文文档](https://github.com/451518849/epub_kitty/blob/master/README_CN.md)
## Install
	dependencies:
	  epub_kitty: ^0.1.7

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
	 */
	EpubReader.open('bookPath');
	
	// page channel used to monitor the current number of pages (only on the ios side)
	  static const pageChannel = const EventChannel('com.xiaofwang.epub_kitty/page');

## Tool Language
#### iOS
plugin in ios default language is en,if you are chinese,you should configuare Localizable.strings. And you can see example to match.

#### android
android default lanuage is chinese before 0.1.7 and later is en, if you used before 0.1.7 ,you should use res/strings.xml to match chinese. see [issue #7](https://github.com/451518849/epub_kitty/issues/7)

## issues
if you can not install it, mybe it exists some confict in your plugins or may not full-install.

more solutions see issuses.

Good luck to you !
 
### QQ Communication：
QQ：853797155，Welcome to exchange questions and technologies!

### Welcome TO PR
	
