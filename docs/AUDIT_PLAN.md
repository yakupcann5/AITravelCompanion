# TravelCompanion — Kod Denetim Plani (Audit Plan)

> **Olusturulma tarihi:** 2026-03-31 | **Son guncelleme:** 2026-03-31
> **Kaynaklar:** Expert (32 bulgu + 6 guvenlik), Senior (17 bulgu), Mid (6 bulgu)
> **Tekrar eden bulgular birlestirildi** — toplam ~45 benzersiz bulgu + 6 guvenlik bulgusu

---

## 1. Proje Saglik Durumu Ozeti

| Kategori | Kritik | Yuksek | Orta | Dusuk | Toplam |
|----------|--------|--------|------|-------|--------|
| Bug / Islevsellik | 5 | 7 | 10 | 3 | 25 |
| Test Eksikligi | — | — | 5 | — | 5 |
| Kod Kalitesi / Hardcoded | — | — | 3 | 4 | 7 |
| Mimari / Yapi | — | — | 2 | 2 | 4 |
| Build / CI | — | — | 1 | — | 1 |
| **Toplam** | **5** | **7** | **21** | **9** | **~42** |

**En kritik bulgular:**
1. Gallery AI gorsel uretimi tamamen kirik — hardcoded Unsplash URL + reflection ByteArray(0)
2. DAO dogrudan ViewModel'a inject — Clean Architecture ihlali (Translator + Gallery)
3. Clipboard kopyalama calismıyor (Translator)
4. LaunchedEffect(Unit) ile effect collection — config change'de kaybolabilir
5. Gallery cift snackbar sorunu

---

## 2. Sayfa Bazli Eksikler

### 2.1 Explore

| ID | Onem | Bulgu | Kaynak |
|----|------|-------|--------|
| B-06 | Kritik | ExploreRoute:32 → LaunchedEffect(Unit) ile effect collect, config change'de kaybolabilir | Expert |
| B-12 | Yuksek | ExploreViewModel:59-77 → loadCities() error handling yok | Expert |
| B-09 | Yuksek | CityDetailPane → Tablet'te "Planla" butonu yok, navigation kirik | Expert |
| B-20 | Orta | ExploreScreen:240-254 → popularCities bosken empty state yok | Expert |
| UI-2 | Orta | ExploreScreen → Filtrelenmis liste bosken mesaj yok | Senior |
| T-01 | Orta | ExploreViewModelTest → ShowCityDetail/DismissCityDetail test yok + 3 senaryo eksik | Expert+Senior |

### 2.2 Planner

| ID | Onem | Bulgu | Kaynak |
|----|------|-------|--------|
| B-06 | Kritik | PlannerRoute:29 → LaunchedEffect(Unit) ile effect collect, config change'de kaybolabilir | Expert |
| B-07 | Yuksek | PlannerViewModel:61-76 → voiceTranscript hic guncellenmez | Expert |
| B-08 | Yuksek | PlannerViewModel → VoiceAssistantService entegrasyonu yok, mikrofon butonu dekoratif | Expert |
| BUG-3 | Yuksek | PlannerScreen:118 → Column fillMaxSize eksik, GeneratingContent ve EmptyPlanContent daralmis | Senior |
| B-11 | Yuksek | Navigator.kt:23 → goBack() crash riski (error() firlatiyor) | Expert |
| B-16 | Orta | PlannerScreen:252 → selectedTab local state, MVI disi | Expert |
| B-17 | Orta | PlannerScreen:319 → MapContent hardcoded Istanbul koordinatlari | Expert |
| B-25 | Dusuk | PlannerScreen:415 → PlaceCard rating Float formatlanmamis | Expert |
| UI-3 | Dusuk | PlannerScreen'deki private composable'larda Modifier parametresi eksik | Senior |
| Mid-3 | Orta | PlannerScreen:349 → Hardcoded polyline color `Color(0xFF1976D2)` | Mid |
| T-02 | Orta | PlannerViewModelTest → 4 Voice intent + 5 senaryo eksik | Expert+Senior |

### 2.3 Translator

| ID | Onem | Bulgu | Kaynak |
|----|------|-------|--------|
| B-01 | Kritik | TranslatorViewModel:6 → DAO dogrudan inject (Clean Architecture ihlali) | Expert+Senior |
| B-05 | Kritik | TranslatorRoute:36 → CopyResult clipboard'a gercekten kopyalamiyor, sadece snackbar | Expert |
| B-10 | Yuksek | TranslatorScreen → error state UI'da render edilmiyor | Expert+Senior |
| B-14 | Orta | TranslatorState:22 → error: String? diger feature'larla tutarsiz (UiText olmali) | Expert |
| B-15 | Orta | TranslatorScreen:184 → history Column ile render, LazyColumn olmali (performans) | Expert |
| Mid-2 | Orta | TranslatorScreen:221 → Hardcoded "->" string | Mid |
| T-05 | Orta | TranslatorViewModelTest → CopyResult clipboard testi + 4 edge case eksik | Expert+Senior |

### 2.4 Gallery

| ID | Onem | Bulgu | Kaynak |
|----|------|-------|--------|
| B-03 | Kritik | GalleryViewModel:89 → generateImage() hardcoded Unsplash URL — AI gorsel uretimi hic calismiyor | Expert |
| B-04 | Kritik | FirebaseAiImageService:42-56 → Reflection ile ByteArray extract, basarida ByteArray(0) donuyor | Expert |
| B-02 | Kritik | GalleryViewModel:6 → DAO dogrudan inject (Clean Architecture ihlali) | Expert+Senior |
| BUG-1 | Kritik | GalleryRoute → Cift snackbar (ImageSaved + ShowSnackbar ayni anda emit) | Senior |
| B-13 | Yuksek | GalleryViewModel:57-76 → prepopulateWithSamples() race condition | Expert |
| B-18 | Orta | GalleryState:15 → error field hic guncellenmez (dead state) | Expert |
| B-19 | Orta | GalleryScreen:76-83 → expanded layout'da ImageDetailSheet cakismasi | Expert |
| UI-1 | Orta | GalleryScreen → Bos galeri empty state yok | Senior |
| T-03 | Orta | GalleryViewModelTest → GenerateImage success + 4 kritik senaryo eksik | Expert+Senior |

---

## 3. Core Modul Sorunlari

### 3.1 Navigation (core:navigation / app)

| ID | Onem | Bulgu |
|----|------|-------|
| B-11 | Yuksek | Navigator.kt:23 → goBack() bos stack'te error() firlatiyor, crash riski |
| B-06 | Kritik | ExploreRoute + PlannerRoute → LaunchedEffect(Unit) effect collection, lifecycle-aware olmali |
| T-04 | Orta | NavigatorTest → hic test yok |

### 3.2 AI (core:ai)

| ID | Onem | Bulgu |
|----|------|-------|
| B-04 | Kritik | FirebaseAiImageService:42-56 → Reflection ByteArray(0) donuyor, image generation kirik |
| B-26 | Dusuk | AiTextService → summarize() ve rewrite() kullanilmiyor (dead code) |
| B-27 | Dusuk | FirebaseAiPlannerService:114 → cityId string'den turetiyor, cakisma riski |

### 3.3 Data (core:data)

| ID | Onem | Bulgu |
|----|------|-------|
| B-21 | Orta | OfflineFirstCityRepository → IoDispatcher kullanilmiyor |
| ARCH-1 | Orta | DispatchersModule tanimli ama hicbir repository'de kullanilmiyor |

---

## 4. Test Eksiklikleri

| ID | Modul | Eksik Senaryolar | Kaynak |
|----|-------|-------------------|--------|
| T-01 | ExploreViewModelTest | ShowCityDetail, DismissCityDetail + 3 ek senaryo | Expert+Senior |
| T-02 | PlannerViewModelTest | 4 Voice intent testi + 5 ek senaryo | Expert+Senior |
| T-03 | GalleryViewModelTest | GenerateImage success, error, loading + 4 kritik senaryo | Expert+Senior |
| T-04 | NavigatorTest | Hic test yok — goBack(), navigateTo() test edilmeli | Expert |
| T-05 | TranslatorViewModelTest | CopyResult clipboard, history, 4 edge case | Expert+Senior |

**Mevcut durum:** 122 test method, 17 dosya
**Eksik:** ~25+ yeni test method gerekiyor
**Integration / UI test:** Hic yok (ayri sprint maddesi)

---

## 5. Kod Kalitesi / Hardcoded String Sorunlari

### 5.1 Hardcoded Stringler

| Dosya | Satir | Deger | Oneri |
|-------|-------|-------|-------|
| TopLevelDestination.kt | 28,33,38,43 | "Kesfet", "Planla", "Ceviri", "Galeri" | `stringResource(R.string.xxx)` |
| TranslatorScreen.kt | 221 | "->" | `stringResource(R.string.arrow_separator)` |
| PlannerState.kt | 33-36 | availableInterests Turkce listesi | `stringArrayResource` veya config |
| TranslatorState.kt | 30-41 | supportedLanguages Turkce listesi | `stringArrayResource` veya config |

### 5.2 Hardcoded Degerler

| Dosya | Satir | Deger | Oneri |
|-------|-------|-------|-------|
| PlannerScreen.kt | 349 | `Color(0xFF1976D2)` polyline | Theme rengi kullan |
| PlannerScreen.kt | 319 | Istanbul koordinatlari (41.0, 28.9) | Secili sehrin koordinatlarini kullan |
| GalleryViewModel.kt | 89 | Unsplash URL | Gercek AI image generation |

### 5.3 Lokalizasyon

- `values-tr/` ve `values-en/` klasorleri mevcut degil
- Coklu dil destegi icin string resource'larin locale bazli ayrilmasi gerekiyor

---

## 6. Onceliklendirmis Task Listesi

### P0 — Kritik (Uygulamanin temel islevselligini etkileyen)

| Task | Baslik | Aciklama | Buyukluk | Etkilenen Dosyalar |
|------|--------|----------|----------|--------------------|
| TASK-01 | Gallery AI gorsel uretimi duzelt | B-03: GalleryViewModel hardcoded Unsplash URL kaldir, gercek AiImageService cagrisini bagla. B-04: FirebaseAiImageService reflection ByteArray(0) sorununu coz | L | `GalleryViewModel.kt`, `FirebaseAiImageService.kt` |
| TASK-02 | DAO injection'i Clean Architecture'a tasi | B-01/B-02: TranslatorViewModel ve GalleryViewModel'dan DAO'yu cikar, UseCase/Repository katmani uzerinden erisim sagla | M | `TranslatorViewModel.kt`, `GalleryViewModel.kt`, yeni UseCase dosyalari |
| TASK-03 | Clipboard kopyalama islevini implement et | B-05: TranslatorRoute CopyResult effect'inde ClipboardManager ile gercek kopyalama yap | S | `TranslatorRoute.kt` |
| TASK-04 | Effect collection'i lifecycle-aware yap | B-06: ExploreRoute ve PlannerRoute'ta LaunchedEffect(Unit) yerine `repeatOnLifecycle` veya uygun lifecycle pattern kullan | M | `ExploreRoute.kt`, `PlannerRoute.kt` |
| TASK-05 | Gallery cift snackbar sorununu duzelt | BUG-1: GalleryRoute'ta ImageSaved ve ShowSnackbar effect'lerini birlestir veya cakismayi onle | S | `GalleryRoute.kt`, `GalleryViewModel.kt` |

### P1 — Yuksek (Onemli ozellikler kirik veya eksik)

| Task | Baslik | Aciklama | Buyukluk | Etkilenen Dosyalar |
|------|--------|----------|----------|--------------------|
| TASK-06 | Voice assistant entegrasyonunu tamamla | B-07 + B-08: VoiceAssistantService'i PlannerViewModel'a entegre et, voiceTranscript guncellenmesini sagla | L | `PlannerViewModel.kt`, `VoiceAssistantService.kt` |
| TASK-07 | Translator error state'i UI'da goster | B-10: TranslatorScreen'e error state rendering ekle | S | `TranslatorScreen.kt` |
| TASK-08 | PlannerScreen layout duzeltmesi | BUG-3: Column'a fillMaxSize ekle, GeneratingContent ve EmptyPlanContent duzgun gorunsun | S | `PlannerScreen.kt` |
| TASK-09 | CityDetailPane tablet navigation duzelt | B-09: Tablet layout'ta "Planla" butonunu ekle, planner'a navigasyonu sagla | M | `CityDetailPane.kt` (ExploreScreen icinde) |
| TASK-10 | Navigator goBack() crash riskini gider | B-11: Bos stack kontrolu ekle, error() yerine guvenli davranis (no-op veya fallback) | S | `Navigator.kt` |
| TASK-11 | ExploreViewModel error handling ekle | B-12: loadCities() icinde hata yakalama ve state guncelleme ekle | S | `ExploreViewModel.kt` |
| TASK-12 | Gallery race condition duzelt | B-13: prepopulateWithSamples() icinde Mutex veya uygun senkronizasyon | M | `GalleryViewModel.kt` |

### P2 — Orta (Kalite, performans ve tutarlilik)

| Task | Baslik | Aciklama | Buyukluk | Etkilenen Dosyalar |
|------|--------|----------|----------|--------------------|
| TASK-13 | Hardcoded string'leri string resource'a tasi | Mid-1/B-22 + Mid-2 + B-23 + B-24: Tab label'lari, "->" string, interests listesi, languages listesi | M | `TopLevelDestination.kt`, `TranslatorScreen.kt`, `PlannerState.kt`, `TranslatorState.kt`, `strings.xml` |
| TASK-14 | Empty state'leri implement et | B-20 + UI-1 + UI-2: Explore (popular + filtered bos), Gallery (bos galeri) icin empty state composable'lari | M | `ExploreScreen.kt`, `GalleryScreen.kt` |
| TASK-15 | Translator error type tutarliligi | B-14: `error: String?` → `UiText` sealed class ile diger feature'larla uyumlu hale getir | S | `TranslatorState.kt`, `TranslatorViewModel.kt`, `TranslatorScreen.kt` |
| TASK-16 | Translator history performans iyilestirmesi | B-15: Column → LazyColumn gecisi | S | `TranslatorScreen.kt` |
| TASK-17 | PlannerScreen selectedTab MVI'a tasi | B-16: Local state yerine MVI State'e tasi, Intent ile guncelle | S | `PlannerScreen.kt`, `PlannerViewModel.kt`, `PlannerState.kt` |
| TASK-18 | Gallery error state duzelt | B-18: error field'i guncellenmesi sagla, UI'da goster | S | `GalleryViewModel.kt`, `GalleryScreen.kt` |
| TASK-19 | Gallery expanded layout cakismasi | B-19: ImageDetailSheet tablet/expanded layout'ta duzgun calissin | M | `GalleryScreen.kt` |
| TASK-20 | Dispatcher kullanimini duzelt | B-21 + ARCH-1: OfflineFirstCityRepository'de IoDispatcher kullan, DispatchersModule'u aktif et | S | `OfflineFirstCityRepository.kt`, ilgili repository'ler |
| TASK-21 | Hardcoded polyline color ve koordinatlar | Mid-3 + B-17: Theme rengi kullan, sehir koordinatlarini dinamik yap | S | `PlannerScreen.kt` |
| TASK-22 | compileSdk / targetSdk uyumsuzlugu | BUILD-1: compileSdk=36 iken targetSdk=35 — uyumlu hale getir | S | `build.gradle.kts` (convention plugin veya app) |
| TASK-23 | Test coverage artirimi | T-01 ~ T-05: Explore, Planner, Gallery, Translator ViewModel + Navigator testleri (~25 yeni test) | XL | Tum `*ViewModelTest.kt` + yeni `NavigatorTest.kt` |

### P3 — Dusuk (Iyilestirme / temizlik)

| Task | Baslik | Aciklama | Buyukluk | Etkilenen Dosyalar |
|------|--------|----------|----------|--------------------|
| TASK-24 | PlaceCard rating formatlama | B-25: Float rating degerini 1 ondalik basamaga formatla | S | `PlannerScreen.kt` (PlaceCard) |
| TASK-25 | Dead code temizligi | B-26: AiTextService'ten kullanilmayan summarize() ve rewrite() metodlarini kaldir | S | `AiTextService.kt`, implementasyonlari |
| TASK-26 | cityId cakisma riski | B-27: FirebaseAiPlannerService'te cityId uretimini guvenli hale getir | S | `FirebaseAiPlannerService.kt` |
| TASK-27 | Composable Modifier best practice | UI-3: PlannerScreen private composable'lara Modifier parametresi ekle | M | `PlannerScreen.kt` |
| TASK-28 | Preview fonksiyonlari ekle | UI-4: Error, searching, generating state'ler icin Preview composable'lar | M | Tum Screen dosyalari |
| TASK-29 | @author tag tamamlama | Mid-6: Eksik dosyalara @author tag ekle | S | Tum eksik Kotlin dosyalari |

---

## Toplam Is Tahmini

| Oncelik | Task Sayisi | S | M | L | XL |
|---------|-------------|---|---|---|----|
| P0 Kritik | 5 | 2 | 2 | 1 | — |
| P1 Yuksek | 7 | 4 | 2 | 1 | — |
| P2 Orta | 13 | 8 | 3 | — | 1 (test) |
| P3 Dusuk | 8 | 5 | 3 | — | — |
| **Toplam** | **33** | **19** | **10** | **2** | **1** |

> *Not: TASK-30 ~ TASK-33 guvenlik audit'inden eklendi (Bolum 7)*

---

## Bagimlilık Grafigi

```
TASK-01 (Gallery AI fix) ─── bagimsiz, hemen baslanabilir
TASK-02 (DAO → UseCase) ─── bagimsiz, hemen baslanabilir
  └── TASK-23 (Test coverage) buna bagimli (yeni UseCase'ler test edilmeli)
TASK-03 (Clipboard) ─── bagimsiz
TASK-04 (Lifecycle effect) ─── bagimsiz
TASK-05 (Cift snackbar) ─── bagimsiz
TASK-06 (Voice assistant) ─── bagimsiz ama L buyukluk
TASK-07 (Translator error) → TASK-15 (UiText) sonrasi daha temiz olur
TASK-13 (String audit) ─── bagimsiz, paralel yapilabilir
TASK-22 (SDK uyumsuzluk) ─── bagimsiz, hemen baslanabilir
TASK-23 (Test coverage) → TASK-02 sonrasi (yeni UseCase'ler icin)
```

---

## 7. Guvenlik Audit Bulgulari

> **Tarih:** 2026-03-31 | **Kaynak:** Expert guvenlik audit'i

### Pozitif Bulgular
- `google-services.json` ve `local.properties` `.gitignore`'da
- MAPS_API_KEY `local.properties` → `manifestPlaceholders` ile inject
- Kaynak kodda hardcoded secret YOK
- Firebase App Check dogru kurulmus (Debug → DebugProvider, Release → PlayIntegrity)
- `android:allowBackup="false"`, R8 minification aktif
- Ktor'da certificate bypass yok, release'te `LogLevel.NONE`
- **Kritik/Yuksek guvenlik bulgusu: 0**

### Orta Onem (3 Bulgu)

| ID | Bulgu | Dosya | Duzeltme |
|----|-------|-------|----------|
| SEC-01 | `shrinkResources` eksik (release build) | `app/build.gradle.kts:47-55` | `isShrinkResources = true` ekle |
| SEC-02 | Room DB sifreleme yok (plaintext) | `core:database` | SQLCipher veya Keystore degerlendirmesi |
| SEC-03 | Ktor DEBUG'da `LogLevel.HEADERS` — gelecekte auth token riski | `NetworkModule.kt:44` | `LogLevel.INFO` veya `LogLevel.NONE` |

### Dusuk Onem (3 Bulgu)

| ID | Bulgu | Duzeltme |
|----|-------|----------|
| SEC-04 | Network Security Config eksik | `network_security_config.xml` olustur, cleartext kapat |
| SEC-05 | Firebase API Key kisitlamasi dogrulanamadi | Firebase Console'da API key kisitla |
| SEC-06 | Certificate pinning yok | Custom backend icin opsiyonel pin ekle |

### Guvenlik Task'lari (AUDIT_PLAN'a eklenen)

| Task | Baslik | Onem | Buyukluk |
|------|--------|------|----------|
| TASK-30 | shrinkResources ekle | P2 | S |
| TASK-31 | Ktor debug log seviyesini dusur | P2 | S |
| TASK-32 | Network Security Config olustur | P3 | S |
| TASK-33 | Room DB sifreleme degerlendirmesi | P3 | M |

---

## Not

- Bu plan, IMPLEMENTATION_PLAN.md'deki Sprint A/B/C ile birlestirilmelidir
- IMPLEMENTATION_PLAN.md'deki bazi maddeler (ornegin WindowSizeClass, Firebase App Check, JaCoCo Threshold) bu audit'te kapsanmamistir — onlar ayri sprint maddeleri olarak devam eder
- Deep link konusunda Mid "eksik" demis ancak IMPLEMENTATION_PLAN.md'de 2 intent-filter tanimli gorulmektedir — dogrulama gerekebilir
- SEC-05 (Firebase API key kisitlamasi) kaynak koddan dogrulanamaz, Firebase Console'dan kontrol edilmeli — lead'e iletildi