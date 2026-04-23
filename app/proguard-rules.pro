# ProGuard kuralları — minifyEnabled=false olduğu için büyük etkisi yok
# İleride R8 aktifleştirilirse gerekli olacak
-keep class androidx.webkit.** { *; }
-keep class com.dersaneai.app.** { *; }
