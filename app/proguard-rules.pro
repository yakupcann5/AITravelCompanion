# Travel Companion ProGuard Rules

# Keep Kotlin serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}

# Keep Room entities
-keep class com.travel.companion.core.database.entity.** { *; }

# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Firebase AI
-keep class com.google.firebase.ai.** { *; }
-keep class com.google.firebase.ai.type.** { *; }

# Ktor + OkHttp
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**
-keep class io.ktor.client.engine.okhttp.** { *; }
-keepclassmembers class io.ktor.** { volatile <fields>; }
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }

# Maps Compose
-keep class com.google.maps.android.compose.** { *; }
-keep class com.google.android.gms.maps.** { *; }

# Coil 3
-keep class coil3.** { *; }
-dontwarn coil3.**

# kotlinx.serialization - model classes
-keepclassmembers @kotlinx.serialization.Serializable class ** {
    *** Companion;
    kotlinx.serialization.KSerializer serializer(...);
}

# Navigation 3 route keys
-keep class com.travel.companion.core.model.*Route { *; }
-keep class com.travel.companion.core.model.*Route$* { *; }
