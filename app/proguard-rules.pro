
-keep class com.example.internshipapp.data.remote.dto.PostsAnswerDto { *; }
-keep class com.example.internshipapp.data.remote.dto.PostsAnswerItemDto { *; }
-keep class com.example.internshipapp.data.remote.dto.CommentsAnswerDto { *; }
-keep class com.example.internshipapp.data.remote.dto.CommentsAnswerDtoItem { *; }

-keep class com.google.gson.reflect.TypeToken
-keep class * extends com.google.gson.reflect.TypeToken
-keep public class * implements java.lang.reflect.Type

-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE