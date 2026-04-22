# Add project specific ProGuard rules here.
-keepnames class * { *; }
-keep class * extends org.koin.core.module.Module { *; }
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keep class com.smartaccounting.core.domain.model.** { *; }
-keep class com.smartaccounting.core.data.local.entity.** { *; }
