---
name: android-verification
description: Hushboard projesi icin kapsamli build verification skill. Build, install, lint, test, security ve diff review asamalarini sirasiyla calistirir.
---

# Android Build Verification

Hushboard projesi icin 6 asamali dogrulama sureci. Kod degisikliklerinden sonra calistir.

## Phase 1 — Build

Debug APK'yi derle. Derleme hatasi varsa devam etme.

```bash
./gradlew assembleDebug
```

**Basari kriteri:** `BUILD SUCCESSFUL` mesaji
**Hata durumu:** `build-error-resolver` agent kullan.

## Phase 2 — Install & Launch

APK'yi cihaza yukle ve uygulamayi baslat.

```bash
./gradlew installDebug && /Users/yakupcan/Library/Android/sdk/platform-tools/adb shell am start -n com.hushboard/.ui.splash.SplashActivity
```

**Basari kriteri:** Uygulama cihazda acilir, crash olmaz
**Hata durumu:** Crash log kontrol et:

```bash
/Users/yakupcan/Library/Android/sdk/platform-tools/adb logcat -d | grep -E "FATAL|AndroidRuntime|com.hushboard" | tail -50
```

## Phase 3 — Lint

```bash
./gradlew lint
```

**Basari kriteri:** CRITICAL ve HIGH lint hatalari yok
**Rapor:** `app/build/reports/lint-results-debug.html`

## Phase 4 — Test

```bash
./gradlew test
```

**Basari kriteri:** Tum testler PASS
Belirli test: `./gradlew test --tests "com.hushboard.ExampleUnitTest"`
Instrumented: `./gradlew connectedAndroidTest`

## Phase 5 — Security

### 5a. Hardcoded Secrets
```bash
grep -rn "api[_-]\?key\|apiKey\|API_KEY\|secret\|password\|token" --include="*.kt" app/src/main/ | grep -v "import\|//\|TAG\|companion\|const val TAG"
```

### 5b. Network Security
```bash
cat app/src/main/res/xml/network_security_config.xml 2>/dev/null || echo "UYARI: network_security_config.xml bulunamadi"
grep -n "usesCleartextTraffic\|cleartextTrafficPermitted" app/src/main/AndroidManifest.xml app/src/main/res/xml/network_security_config.xml 2>/dev/null
```

### 5c. Debug Flag
```bash
grep -n "debuggable\|minifyEnabled\|shrinkResources" app/build.gradle* 2>/dev/null
```

### 5d. Log Sanitization
```bash
grep -rn "Log\.\(d\|i\|w\|e\|v\).*\(token\|password\|secret\|key\|auth\)" --include="*.kt" app/src/main/ | grep -v "import\|TAG\|companion"
```

## Phase 6 — Diff Review

```bash
git diff --stat
git diff --staged --stat
git status --short
```

**Kontrol listesi:**
- [ ] Sadece ilgili dosyalar degismis
- [ ] Yeni dosyalar dogru dizinde
- [ ] Import'lar temiz (qualified name yok)
- [ ] Log statement'lari uygun (println yok)
- [ ] KDoc header'lar mevcut (yeni dosyalarda)

## Ozet Rapor Formati

```
## Hushboard Verification Report

| Phase | Durum | Detay |
|-------|-------|-------|
| 1. Build | PASS/FAIL | Build suresi |
| 2. Install | PASS/FAIL | Cihaz durumu |
| 3. Lint | PASS/FAIL | Bulgu sayisi |
| 4. Test | PASS/FAIL | Test sayisi |
| 5. Security | PASS/FAIL | Bulgu sayisi |
| 6. Diff | PASS/FAIL | Dosya sayisi |

Genel Sonuc: PASS / FAIL
```

## Hizli Dogrulama (Kucuk Degisiklikler)

```bash
./gradlew installDebug && /Users/yakupcan/Library/Android/sdk/platform-tools/adb shell am start -n com.hushboard/.ui.splash.SplashActivity
```

## Tam Dogrulama (PR Oncesi)

Tum 6 phase'i sirayla calistir. Herhangi bir phase FAIL olursa dur ve duzelt.
