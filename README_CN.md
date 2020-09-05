# epub_kitty

![](https://img.shields.io/badge/build-passing-brightgreen)
![](https://img.shields.io/badge/version-0.1.5-orange)
![](https://img.shields.io/badge/platform-flutter-lightgrey)


![](1.jpeg)
![](2.jpeg)


epub_kitty是款epub电子书阅读器，是对开源框架[folioreader](https://folioreader.github.io/FolioReaderKit/)的封装。
[epub_kitty 0.0.4](https://pub.dev/packages/epub_kitty)

## 安装
	dependencies:
	  epub_kitty: ^0.1.5

## 使用
   
    first step
    /**
     * @identifier (android 无效)
     * @themeColor
     * @scrollDirection (android 无效)
     * @allowSharing (android 无效)
     */
    EpubKitty.setConfig("book", "#32a852","vertical",true);
    
    second step
	 /**
	 * @bookPath 电子书文档路径
	 */
	EpubReader.open('bookPath');
	
	// page channel 用来监听当前页数（仅限ios端）
	  static const pageChannel = const EventChannel('com.xiaofwang.epub_kitty/page');

### QQ技术交流：
群聊号：853797155，欢迎交流问题和技术！

### 欢迎PR
	
