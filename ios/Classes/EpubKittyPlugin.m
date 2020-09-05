#import "EpubKittyPlugin.h"
#import <epub_kitty/epub_kitty-Swift.h>

@implementation EpubKittyPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftEpubKittyPlugin registerWithRegistrar:registrar];
}
@end
