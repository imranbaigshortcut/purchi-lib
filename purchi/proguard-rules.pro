# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-printmapping mapping.txt

-keepattributes SourceFile,LineNumberTable        # Keep file names and line numbers.
-keep public class * extends java.lang.Exception  # Optional: Keep custom exceptions.

-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken
-keepnames class idea.pti.insaf.purchi.data.Candidate { *; }
-keep class idea.pti.insaf.purchi.data.Candidate  { *; }
-keepnames class idea.pti.insaf.purchi.api.Voter { *; }
-keep class idea.pti.insaf.purchi.api.Voter { *; }


-keep class idea.pti.insaf.purchi.api.Voter1 { *; }
-keepnames class idea.pti.insaf.purchi.api.Voter1 { *; }
-keep class idea.pti.insaf.purchi.api.Na { *; }
-keepnames class idea.pti.insaf.purchi.api.Na { *; }
-keep class idea.pti.insaf.purchi.api.Pa { *; }
-keepnames class idea.pti.insaf.purchi.api.Pa { *; }
