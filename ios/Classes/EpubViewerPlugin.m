#import "EpubViewerPlugin.h"
#import <epub_viewer/epub_viewer-Swift.h>

@implementation EpubViewerPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftEpubViewerPlugin registerWithRegistrar:registrar];
}
@end
