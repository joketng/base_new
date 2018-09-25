# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in d:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#########-----混淆的基本配置，一般不需要改变------########
-dontwarn
-optimizationpasses 5                                                    #压缩级别
-dontusemixedcaseclassnames                                              #不使用大小写混合
-dontskipnonpubliclibraryclasses                                         #不忽略非公共的类库的类
-dontskipnonpubliclibraryclassmembers                                    #不忽略非公共类库的类成员
-dontpreverify                                                           #不做预校验，加快混淆速度
-verbose                                                                 #混淆后生成映射文件
-printmapping proguardMapping.txt                                        #映射文件名称
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/* #指定混淆算法

-keepattributes *Annotation*               #不混淆注解
-keep class * extends Java.lang.annotation.Annotation { *; }
-keepattributes Signature                  #不混淆泛型
-keepattributes SourceFile,LineNumberTable #保留代码行号

-keep public class * extends android.app.Activity                               # 继承Activity的类不被混淆
-keep public class * extends android.app.Application                            # 继承Application的类不被混淆
-keep public class * extends android.app.Service                                # 继承Service的类不被混淆
-keep public class * extends android.content.BroadcastReceiver                  # 继承BroadcastReceiver的类不被混淆
-keep public class * extends android.content.ContentProvider                    # 继承ContentProvider的类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper               # 继承BackupAgentHelper的类不被混淆
-keep public class * extends android.preference.Preference                      # 继承Preference的类不被混淆
-keep public class com.android.vending.licensing.ILicensingService              # 继承ILicensingService的类不被混淆
-keep public class * extends android.support.v4.app.Fragment                    # V4包不被混淆

-dontwarn android.support.**                                                    #不发出警告 引用了v4或者v7包

-keep class org.apache.http.** { *; }                                           #java开源框架类防止报错
-dontwarn org.apache.http.**
-dontwarn android.net.**

-keepclasseswithmembernames class * {                                           # 保持 native 方法不被混淆
    native <methods>;
}

-keep class  com.android.app.** implements  com.android.app.Platform$ICallback {*;}   # 保持内部接口不被混淆

-keepclasseswithmember class * {                                                #不删除eventBus订阅函数
   void onEvent(***);
   void onEvent*(***);
   public void onEvent*(**);
}

-keepclasseswithmembers class * extends anroid.view.View{                       # 保持自定义控件类不被混淆
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {                        # 保持onClick(View v)重写不受影响
   public void *(android.view.View);
}

-keepclassmembers enum * {                                                      # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {                                # 保持 Parcelable 不被混淆
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * implements java.io.Serializable{                      # 保持 serializable 不被混淆
  static final long serialVersionUID;
  private static final java.io.ObjectStreamField[] serialPersistentFields;
  private void writeObject(java.io.ObjectInputStream);
  private void readObject(java.io.ObjectInputStream);
  java.lang.Object writeReplace();
  java.lang.Object readResolve();
}

-keep class **.R$*{                                                            #保证资源类不被混淆
  *;
}

-keepclassmembers  class  *  extends android.support.v4.app.Fragment {        #保留所有OnClick事件
    public void  *(android.view.View);
    public boolean *(android.view.View);
}

-keepclassmembers  class  *  extends android.app.Activity {        #保留所有OnClick事件
    public void  *(android.view.View);
    public boolean *(android.view.View);
}

-keep public class * {                                                         #保留所有实体类
  public void set*(***);
  public *** get*();
  public *** is*();
}

###############----webview----#############
# webview + js
-keepattributes *JavascriptInterface*
# keep 使用 webview 的类
-keepclassmembers class  com.jointem.yxb.html5.server.AsynServiceHandlerImpl {
   public *;
}
-keepclassmembers class  com.jointem.yxb.fragment.HonorWallFragment {
   public *;
}
# keep 使用 webview 的类的所有的内部类
-keepclassmembers  class  com.jointem.yxb.html5.server.AsynServiceHandlerImpl$*{
    *;
}
-keepclassmembers  class  com.jointem.yxb.fragment.HonorWallFragment$*{
    *;
}

#API参数类
-keep class com.jointem.yxb.params.*{
    *;
}

###############----混淆保护项目的部分代码以及引用的第三方jar包library----################

#-libraryjars libs/umeng-analytics-v5.2.4.jar
#-libraryjars libs/alipaysdk.jar
#-libraryjars libs/alipaysecsdk.jar
#-libraryjars libs/alipayutdid.jar
#-libraryjars libs/wup-1.0.0-SNAPSHOT.jar
#-libraryjars libs/weibosdkcore.jar

#三星应用市场需要添加:sdk-v1.0.0.jar,look-v1.0.1.jar
#-libraryjars libs/sdk-v1.0.0.jar
#-libraryjars libs/look-v1.0.1.jar

#-libraryjars universal-image-loader-1.9.3.jar
-keep class com.nostra13.universalimageloader.** { *;}       #引用的图片加载框架universalimageloader

#Gson
-keep class com.google.**{*;}
#友盟
-keep class com.umeng.**{*;}

#permission processer
-dontwarn com.zhy.m.**
-keep class com.zhy.m.** {*;}
-keep interface com.zhy.m.** { *; }
-keep class **$$PermissionProxy { *; }

#支付宝
#-keep class com.alipay.android.app.IAliPay{*;}
#-keep class com.alipay.android.app.IAlixPay{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
#-keep class com.alipay.android.app.lib.ResourceMap{*;}

#个推
#-libraryjars libs/GetuiExt-2.0.3.jar
#-libraryjars libs/GetuiSDK-2.7.0.0.jar
-dontwarn com.igexin.**
-keep class com.igexin.** {*;}

#EventBus
#-libraryjars libs/EventBus2.4.jar
-dontwarn com.ypy.eventbus.**
-keep class com.ypy.eventbus.** {*;}

#Gson
#-libraryjars libs/gson-2.2.2.jar
-keepattributes Signature
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }
#环信
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**
#MPAndroid chat
-dontwarn com.github.mikephil.charting.data.realm.**

#baidumap
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**
#remote-net-request
-keep class com.jointem.plugin.net.**{*;}
#kjframe
-keep class org.kymjs.kjframe.**{*;}
#pulltorefresh
-dontwarn com.handmark.pulltorefresh.library.**
-keep class com.handmark.pulltorefresh.library.** { *;}
-dontwarn com.handmark.pulltorefresh.library.extras.**
-keep class com.handmark.pulltorefresh.library.extras.** { *;}
-dontwarn com.handmark.pulltorefresh.library.internal.**
-keep class com.handmark.pulltorefresh.library.internal.** { *;}

#新浪微博分享
-dontwarn com.weibo.sdk.android.WeiboDialog
-keep class com.sina.weibo.**{*;}
-dontwarn android.net.http.SslError
-dontwarn android.webkit.WebViewClient
-keep public class android.net.http.SslError{
     *;
}
-keep public class android.webkit.WebViewClient{
    *;
}
-keep public class android.webkit.WebChromeClient{
    *;
}
-keep public interface android.webkit.WebChromeClient$CustomViewCallback {
    *;
}
-keep public interface android.webkit.ValueCallback {
    *;
}
-keep class * implements android.webkit.WebChromeClient {
    *;
}

#pinyin4j
#-libraryjars libs/pinyin4j-2.5.0.jar
-dontwarn demo.**
-keep class demo.**{*;}

-dontwarn net.sourceforge.pinyin4j.**
-keep class net.sourceforge.pinyin4j.**{*;}
-keep class net.sourceforge.pinyin4j.format.**{*;}
-keep class net.sourceforge.pinyin4j.format.exception.**{*;}

############混淆保护自己项目的部分代码以及引用的第三方jar包library-end##################
