#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#
Pod::Spec.new do |s|
  s.name             = 'epub_kitty'
  s.version          = '0.1.7'
  s.summary          = 'A epub reader flutter plugin project.'
  s.description      = <<-DESC
A new flutter plugin project.
                       DESC
  s.homepage         = 'https://github.com/451518849/epub_kitty.git'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'xiaofwang' => '451518849@qq.com' }
  s.source           = { :path => '.' }
  
  
  s.source_files = [
  'Classes/**/*',
  ]

  s.dependency 'Flutter'
  
  s.dependency 'epub_kitty_ios', '0.1.0'
  s.ios.deployment_target = '9.0'

  
end
