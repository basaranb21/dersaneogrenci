# DersaneAI Android App

TYT/AYT dershane analiz sistemi için WebView tabanlı Android uygulaması.

## 🎯 Özellikler

- ✅ **Herhangi bir dershane** için çalışır — ilk açılışta sunucu URL'ini sorar
- ✅ **Push bildirim** desteği (Web Push, Firebase'siz)
- ✅ **Kamera + galeri** erişimi (foto yükleme için)
- ✅ **Mikrofon** erişimi (sesli anlatım, chatbot)
- ✅ **Cookie saklama** (bir kez login → kalıcı)
- ✅ **HTTP** desteği (dershane LAN'ında kullanım için)
- ✅ **Offline cache** (service worker üzerinden)
- ✅ **Geri tuşu** doğru çalışır

## 🚀 APK Nasıl Üretilir (GitHub Actions — Otomatik)

### Adım 1: GitHub'a yeni repo oluştur
1. [github.com/new](https://github.com/new) → `dersaneai-android` adıyla
2. Public veya private fark etmez
3. Boş bırak (README ekleme)

### Adım 2: Bu klasörü repo'ya yükle
```bash
cd android-app
git init
git add .
git commit -m "İlk sürüm"
git branch -M main
git remote add origin https://github.com/KULLANICI_ADIN/dersaneai-android.git
git push -u origin main
```

### Adım 3: GitHub Actions otomatik build başlar
- Repo'nun **Actions** sekmesine git
- "Build DersaneAI APK" workflow'u ~3 dakika çalışır
- Success ✅ olunca iki sonuç:

**A) Artifacts** (her build)
- Actions → ilgili run → Artifacts → **dersaneai-apk** → indir (ZIP)

**B) Releases** (main'e push olduğunda)
- Repo ana sayfasında **Releases** bölümüne git
- Son release → **dersaneai.apk** → doğrudan indir

### Adım 4: APK'yı sisteme yükle
DersaneAI `/mobil-app/` sayfasında (admin):
- **"🚀 GitHub Release'den İndir"** sekmesi → URL yapıştır:
  `https://github.com/KULLANICI_ADIN/dersaneai-android/releases/latest`
- **"🌐 GitHub'dan İndir"** → sistem APK'yı otomatik çeker

## 🔧 Lokal Build (Android Studio ile)

1. Android Studio ile `android-app/` klasörünü aç
2. Sync gradle
3. Run → Build → Generate Signed Bundle / APK
4. `app/build/outputs/apk/release/app-release.apk` çıkar

## 📱 Telefona Yükleme

1. APK'yı indir
2. Ayarlar → Güvenlik → "Bilinmeyen kaynaklar" → bu tarayıcıya izin
3. APK'ya tıkla → Yükle
4. İlk açılış: Sunucu URL'ini gir (örn: `http://192.168.1.6:8765/`)
5. "Test Et" → başarılıysa **Kaydet ve Başla**
6. Her şey hazır — login olup bildirim izni ver

## 🔐 URL'i Değiştirmek

Yanlış URL girdiysen veya dershane değiştiysen:
- Uygulamayı kaldır/yeniden kur
- VEYA: Uygulama verilerini temizle (Ayarlar → Uygulamalar → DersaneAI → Depolama → Verileri temizle)

## ⚠️ Önemli Notlar

### HTTP vs HTTPS
Uygulama `cleartextTrafficPermitted=true` ile HTTP destekliyor — dershane LAN'ında `192.168.*.*` gibi adreslere gidebilir. Dış dünya (internet) kullanıyorsan **HTTPS** kullan (sertifika ücretsiz Let's Encrypt).

### Aynı Wi-Fi Şartı
Telefon ve dershane bilgisayarı aynı Wi-Fi ağında olmalı. Farklı ağda kullanım için:
- Dershane sabit IP + port forwarding (ileri düzey)
- Veya VPN kurulumu

### Android Versiyonu
- **Minimum:** Android 6.0 (API 23)
- **Önerilen:** Android 8.0+ (Push bildirim için)
- Android 13+ bildirim için ayrıca izin gerekir (otomatik sorar)

## 🛠️ Geliştirme

### Proje Yapısı
```
android-app/
├── .github/workflows/build-apk.yml  ← GitHub Actions CI
├── app/
│   ├── build.gradle                 ← App gradle config
│   └── src/main/
│       ├── AndroidManifest.xml      ← İzinler, aktiviteler
│       ├── java/com/dersaneai/app/
│       │   ├── SetupActivity.java   ← İlk açılış URL ayarı
│       │   └── MainActivity.java    ← WebView ana ekran
│       └── res/                     ← Layout, drawable, strings
├── build.gradle
├── settings.gradle
└── gradle.properties
```

### Özelleştirme
- **Uygulama adı:** `app/src/main/res/values/strings.xml` → `app_name`
- **Paket adı:** `app/build.gradle` → `applicationId`
- **Renkler:** `app/src/main/res/values/colors.xml`
- **İkon:** `app/src/main/res/mipmap-*/ic_launcher.png` (otomatik PWA'dan)

## 📦 İmzalama

Varsayılan: **Debug signing** (Play Store için yeterli DEĞİL, ama özel dağıtım için OK).

Play Store'a yüklemek istersen:
1. `keytool` ile release keystore oluştur
2. `app/build.gradle`'a `signingConfigs.release` ekle
3. GitHub secrets'a keystore dosyasını ekle (base64 encoded)
4. Workflow'u imzalı build'e göre güncelle

## 📞 Destek

- DersaneAI ana proje: [basaranb21/...](https://github.com/basaranb21)
- Sorular için issue aç

## 📄 Lisans

MIT — istediğin gibi kullan, değiştir, dağıt.
