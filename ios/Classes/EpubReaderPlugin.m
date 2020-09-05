#import "EpubReaderPlugin.h"
#import <epub_reader/epub_reader-Swift.h>

@implementation EpubReaderPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftEpubReaderPlugin registerWithRegistrar:registrar];
}
@end
