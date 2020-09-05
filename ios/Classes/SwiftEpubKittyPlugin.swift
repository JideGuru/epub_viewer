import Flutter
import UIKit
import epub_kitty_ios

public class SwiftEpubKittyPlugin: NSObject, FlutterPlugin,FolioReaderPageDelegate,FlutterStreamHandler {
    
    let folioReader = FolioReader()
    static var pageResult: FlutterResult? = nil
    static var pageChannel:FlutterEventChannel? = nil
    
    var config: EpubConfig?
    
    
    //12.13
    public static func register(with registrar: FlutterPluginRegistrar) {
      let channel = FlutterMethodChannel(name: "epub_kitty", binaryMessenger: registrar.messenger())
      let instance = SwiftEpubKittyPlugin()
        
      pageChannel = FlutterEventChannel.init(name: "com.xiaofwang.epub_reader/page",
                                  binaryMessenger: registrar.messenger());
      
      registrar.addMethodCallDelegate(instance, channel: channel)
    }

    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
      

      switch call.method {
      case "setConfig":
        let arguments = call.arguments as![String:Any]
        let Identifier = arguments["identifier"] as! String
        let scrollDirection = arguments["scrollDirection"] as! String
        let color = arguments["themeColor"] as! String
        let allowSharing = arguments["allowSharing"] as! Bool

        self.config = EpubConfig.init(Identifier: Identifier,tintColor: color,allowSharing: allowSharing,scrollDirection: scrollDirection)

        break
      case "open":
          setPageHandler()
          let arguments = call.arguments as![String:Any]
          let bookPath = arguments["bookPath"] as! String
          self.open(epubPath: bookPath)

          break
      case "close":
          self.close()
          break
      default:
          break
      }
  //    result("iOS " + UIDevice.current.systemVersion)
    }
      
      private func setPageHandler(){
          SwiftEpubKittyPlugin.pageChannel?.setStreamHandler(self)

      }
      
      public func onListen(withArguments arguments: Any?, eventSink events: @escaping FlutterEventSink) -> FlutterError? {
          SwiftEpubKittyPlugin.pageResult = events
          return nil
      }

      public func onCancel(withArguments arguments: Any?) -> FlutterError? {
          return nil
      }
      
      
      fileprivate func open(epubPath: String) {
           if epubPath == "" {
              return
          }

          let readerVc = UIApplication.shared.keyWindow!.rootViewController ?? UIViewController()
            
        folioReader.presentReader(parentViewController: readerVc, withEpubPath: epubPath, andConfig: self.config!.config, shouldRemoveEpub: true)
          folioReader.readerCenter?.pageDelegate = self
      }

      public func pageWillLoad(_ page: FolioReaderPage) {
          
          print("page.pageNumber:"+String(page.pageNumber))

          if (SwiftEpubKittyPlugin.pageResult != nil){
              SwiftEpubKittyPlugin.pageResult!(String(page.pageNumber))
          }

      }
      
      private func close(){
          folioReader.readerContainer?.dismiss(animated: true, completion: nil)
      }

  }
