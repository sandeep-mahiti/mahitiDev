# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/sindhyapeter/Library/Android/sdk/tools/proguard/proguard-android.txt
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
-dontwarn android.support.**

 #### -- OkHttp --
 -dontwarn com.squareup.okhttp.internal.**
 -dontwarn com.squareup.okhttp.**

 #### -- Apache Commons --
 -dontwarn org.apache.commons.logging.**
 -dontwarn org.apache.commons.**
 -keep class org.apache.http.** { *; }
 -dontwarn org.apache.http.**

 # Retrofit 1.X
 -keep class com.squareup.okhttp.** { *; }
 -keep class retrofit.** { *; }
 -keep interface com.squareup.okhttp.** { *; }

 -dontwarn com.squareup.okhttp.**
 -dontwarn okio.**
 -dontwarn retrofit.**
 -dontwarn rx.**

 -dontwarn retrofit2.**
 -keep class retrofit2.** { *; }
 -keepattributes Signature
 -keepattributes Exceptions

 -keepclasseswithmembers class * {
     @retrofit2.http.* <methods>;
 }

 -keepclasseswithmembers class * {
     @retrofit.http.* <methods>;
 }

 # If in your rest service interface you use methods with Callback argument.
 -keepattributes Exceptions

 # If your rest service methods throw custom exceptions, because you've defined an ErrorHandler.
 -keepattributes Signature


-keepclassmembers class org.mahiti.clt_jar.activity.VideoViewActivity{
public JSONArray *;
}

-keep class javax.lang.model.** { *; }
 -keep class android.** { *; }


 -keep class butterknife.** { *; }
 -dontwarn butterknife.internal.**
 -keep class **$$ViewBinder { *; }
 -keepclasseswithmembernames class * { @butterknife.* <fields>; }
 -keepclasseswithmembernames class * { @butterknife.* <methods>; }
 -keep class net.sqlcipher.** { *; }

 -keep public class android.util.FloatMath
 -dontnote okhttp3.**, okio.**, retrofit2.**, pl.droidsonroids.**

 #For JodaTime
 #https://stackoverflow.com/questions/14025487/proguard-didnt-compile-with-joda-time-used-in-windows
 -dontwarn org.joda.convert.FromString
 -dontwarn org.joda.convert.ToString

 # support-v4
 #https://stackoverflow.com/questions/18978706/obfuscate-android-support-v7-widget-gridlayout-issue
 -dontwarn android.support.v4.**
 -keep class android.support.v4.app.** { *; }
 -keep interface android.support.v4.app.** { *; }
 -keep class android.support.v4.** { *; }
# support-v7
-dontwarn android.support.v7.**
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keep class android.support.v7.** { *; }

# support design
#@link http://stackoverflow.com/a/31028536
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

#error : Note: the configuration refers to the unknown class 'com.google.vending.licensing.ILicensingService'
#solution : @link http://stackoverflow.com/a/14463528
-dontnote com.google.vending.licensing.ILicensingService
-dontnote **ILicensingService

#social auth
-keep class org.brickred.** { *; }
-dontwarn org.brickred.*


-dontwarn javax.servlet.**
-dontwarn org.joda.time.**
-dontwarn org.w3c.dom.**

-dontwarn org.apache.lang.**
#similarly handle other libraries you added
-dontwarn com.google.android.gms.internal.zzhu

-keep class com.google.android.gms.internal.** { *; }
-dontwarn net.authorize.sim.Transaction

-keep public class com.android.vending.licensing.ILicensingService

-keepattributes SourceFile,LineNumberTable
-keep class com.parse.*{ *; }
-dontwarn com.parse.**
-dontwarn com.squareup.picasso.**
-keepclasseswithmembernames class * {
    native <methods>;
}
-dontwarn com.google.android.gms.cast.**
-dontwarn com.google.android.gms.**

-dontwarn android.net.http.AndroidHttpClient

-dontwarn com.android.volley.toolbox.**