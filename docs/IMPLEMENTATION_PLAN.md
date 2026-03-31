# AI Travel Companion — Eksik Tamamlama Planı

## Context

Proje ROADMAP_P2.md'ye göre 5 fazdan oluşuyor (Faz 0-5). Her faz ayrı Explore agent ile derinlemesine analiz edildi. Bu plan, analiz sonuçlarına dayanarak eksikleri öncelik sırasına göre grupluyor.

> **Son güncelleme:** 2026-03-31 — Tüm fazlar yeniden doğrulandı.

---

## Faz Bazlı Analiz Özeti

### Faz 0 — Proje İskeleti (%92)

| Madde | Durum | Detay |
|-------|-------|-------|
| Convention Plugins (5 adet) | ✅ | android-library, compose, hilt, feature, **jacoco** |
| libs.versions.toml | ✅ | Güncel (Kotlin 2.1.21, Compose BOM 2025.05.01) |
| settings.gradle.kts | ✅ | 14 modül register |
| .editorconfig | ✅ | UTF-8, 4 space, max 120, ktlint android_studio |
| detekt config | ✅ | LongMethod:50, LargeClass:800 |
| GitHub Actions CI | ✅ | build + detekt + lint + test + jacocoTestReport + artifact upload |
| core:model (6 data class) | ✅ | City, Place, DayPlan, **TravelPlan**, Route, UserPreference |
| core:common | ✅ | Dispatchers DI (Io/Default/Main), AppResult wrapper (sealed interface) |
| core:designsystem | ✅ | Material 3, Dynamic Color (Android 12+), Typography |
| core:ui | ✅ | LoadingIndicator, ErrorScreen, CityCard |
| Navigation setup | ✅ | **Navigation 3 (NavDisplay + user-owned back stack)** |
| Adaptive navigation | ✅ | NavigationSuiteScaffold (BottomNav/Rail otomatik) |
| **WindowSizeClass** | ❌ | Dependency var (androidx.window 1.4.0) ama kod tarafında kullanılmıyor |

### Faz 1 — Data Layer + Explore (%100)

| Madde | Durum | Detay |
|-------|-------|-------|
| Room DB (Entity, DAO) | ✅ | **4 Entity** (City, TravelPlan, GeneratedImage, TranslationHistory), **4 DAO** |
| Ktor Client | ✅ | OkHttp engine, ContentNegotiation, Logging, 4 endpoint |
| Repositories | ✅ | OfflineFirst pattern (City + TravelPlan), @Binds DI |
| UserPreferencesRepository | ✅ | DataStore Preferences (theme, language, budget) |
| Mappers | ✅ | City + TravelPlan mapper (toDomain + toEntity) |
| CityPrepopulator | ✅ | cities.json → Room (assets'ten okuma, upsert) |
| Explore MVI | ✅ | Intent(**6**), State(**8** field + 2 computed), Effect(2) |
| ExploreScreen | ✅ | StaggeredGrid, SearchBar, 6 section, **ListDetailPaneScaffold** (adaptive) |
| Use Cases (3 adet) | ✅ | GetPopularCities, GetRecentSearches, SearchCityWithAi |
| Tests (52 case) | ✅ | **6 dosya**: ViewModel, Repository(2), Mapper(2), UseCase |

### Faz 2 — AI Core + Planner (%89)

| Madde | Durum | Detay |
|-------|-------|-------|
| AI interfaces (4 service) | ✅ | AiTextService, AiPlannerService, AiImageService, AiCityService |
| Firebase implementations (4 adet) | ✅ | Gemini 2.5 Flash, JSON parsing |
| Fake implementations (4 adet) | ✅ | Test/dev için: Text, Planner, Image, City |
| GeminiNanoClient | ⚠️ | HybridAiTextService var, cloud-only çalışıyor; Nano API stabilize olunca entegre edilecek |
| FeatureAvailabilityChecker | ⚠️ | Dosya var, OnDeviceStatus sealed interface tanımlı, şu an `false` dönüyor |
| HybridAiService | ⚠️ | Var, DI'da AiTextService'e bind edilmiş; on-device first tasarımı hazır, Nano pending |
| Planner MVI | ✅ | Intent(**13** — voice dahil), State(**13** field), Effect(2) |
| PlannerViewModel | ✅ | Full MVI, SavedStateHandle, error handling |
| PlannerScreen | ✅ | Preferences, Itinerary, PlaceCard, **VoiceAssistantSheet**, **GoogleMap** |
| Use Cases (2 adet) | ✅ | GenerateTravelPlan, SaveTravelPlan |
| Maps Compose | ✅ | GoogleMap, Marker, Polyline **aktif kullanılıyor** |
| Maps embed (Tab/side-by-side) | ✅ | PlannerScreen'de harita entegrasyonu mevcut |
| PlannerViewModelTest | ✅ | 11 test method |
| GenerateTravelPlanUseCaseTest | ✅ | 3 test method |

### Faz 3 — Translator + Gallery (%94)

| Madde | Durum | Detay |
|-------|-------|-------|
| Translator MVI | ✅ | Intent(**10** — history dahil), State(**7** field), Effect(2) |
| TranslatorViewModel | ✅ | MVI pattern, HybridAiTextService üzerinden (cloud-only, Nano-ready) |
| TranslatorScreen | ✅ | Dil seçici, swap, source/target, **geçmiş listesi** |
| TranslateTextUseCase | ✅ | AiTextService.translate() (Nano mevcutsa on-device, değilse cloud) |
| Gemini Nano offline çeviri | ⚠️ | HybridAiTextService tasarımda; Nano API stabilize olunca aktif olacak |
| Çeviri geçmişi (history) | ✅ | TranslationHistoryEntity + DAO + UI tam implemente |
| TranslatorViewModelTest | ✅ | 9 test method |
| Gallery MVI | ✅ | Intent(**7** — share dahil), State(**6** field), Effect(**3** — ShareImage dahil) |
| GalleryViewModel | ✅ | MVI pattern, **FirebaseAiImageService** (fake değil) |
| GalleryScreen | ✅ | StaggeredGrid, PromptInput, GalleryImageCard, **ListDetailPaneScaffold** |
| GenerateImageUseCase | ✅ | AiImageService.generateImage() |
| Coil 3 | ✅ | AsyncImage (v3.1.0) kullanılıyor |
| Gerçek image generation | ✅ | Firebase AI (Gemini 2.5 Flash), reflection-based ByteArray extraction |
| ImageDetail ekranı | ✅ | ModalBottomSheet + Adaptive DetailPane (tablet) |
| Share functionality | ✅ | GalleryEffect.ShareImage, UI'da share butonları |
| Image persistence (DB) | ✅ | GeneratedImageEntity + GeneratedImageDao (upsert, favorite, getAll) |
| GalleryViewModelTest | ✅ | 5 test method |

### Faz 4 — Voice + Adaptive Polish (%93)

| Madde | Durum | Detay |
|-------|-------|-------|
| Gemini Live API | ✅ | VoiceAssistantService interface + AudioRecorder (Flow-based audio chunk) |
| VoiceAssistantSheet | ✅ | ModalBottomSheet + mic kontrolü, PlannerScreen'de FAB ile açılıyor |
| ListDetailPaneScaffold | ✅ | ExploreScreen + GalleryScreen'de aktif kullanımda |
| Foldable support | ⚠️ | currentWindowAdaptiveInfo() kullanılıyor (modern yaklaşım); WindowInfoTracker/FoldingFeature yok |
| Predictive back | ✅ | `enableOnBackInvokedCallback="true"` manifest'te; Navigation 3 otomatik handle ediyor |
| Deep links | ✅ | 2 intent-filter: `travelcompanion://explore`, `travelcompanion://planner` |
| State restoration | ✅ | SavedStateHandle (PlannerVM) + rememberSaveable (PlannerScreen, NavigationState) |

### Faz 5 — Test + QA + CI/CD (%53)

| Madde | Durum | Detay |
|-------|-------|-------|
| Unit tests | ⚠️ | **122 test method, 17 dosya**. Coverage hedefi %70'e ulaşılıp ulaşılmadığı ölçülmedi |
| Integration tests | ❌ | Yok |
| UI tests (androidTest) | ❌ | Dizin bile yok |
| CI/CD pipeline | ✅ | Build + Detekt + Lint + Unit Test + JaCoCo Report + Artifact Upload |
| JaCoCo coverage gate | ⚠️ | Convention plugin var, CI'da rapor üretiliyor; **threshold (%70) enforce edilmiyor** |
| API key security | ✅ | `${MAPS_API_KEY}` placeholder, hardcoded secret yok |
| Firebase App Check | ❌ | Yok |
| ProGuard/R8 | ✅ | 48 satır kural: Serialization, Room, Hilt, Firebase AI, Ktor, OkHttp, Maps, Coil 3, Nav3 |
| Baseline Profiles | ❌ | Yok |
| @Stable/@Immutable | ⚠️ | City, Place, DayPlan, TravelPlan, UserPreference'da var; composable'larda yok |
| LeakCanary | ✅ | `debugImplementation(libs.leakcanary)` v2.14 |
| Snackbar TODO'ları | ✅ | **Tamamı implemente edildi** — 4 Route'ta SnackbarHostState + SnackbarHost mevcut |
| String resources | ⚠️ | **68 string resource** (5 app, 1 core/ui, 28 planner, 13 gallery, 10 translator, 11 explore); hala hardcoded olabilecekler kontrol edilmeli |
| @Preview fonksiyonları | ❌ | 0 preview fonksiyonu |
| @author tag'leri | ⚠️ | **42 dosyada** mevcut, geri kalanlar eksik |

---

## Kalan İş Planı (3 Sprint)

> Önceki 7 sprint'lik planın büyük bölümü tamamlandı. Aşağıda yalnızca **kalan işler** listeleniyor.

### Sprint A: Temel Eksikler [P0 — hemen başlanabilir]

**A.1 WindowSizeClass Entegrasyonu [S]**
- `MainActivity.kt` → `calculateWindowSizeClass()`
- `TravelApp.kt` → WindowSizeClass'ı composable'lara ilet
- ExploreScreen, GalleryScreen → grid column sayısı WindowSizeClass'a göre ayarla

**A.2 Firebase App Check [M]**
- `gradle/libs.versions.toml` → firebase-appcheck dependency
- `app/.../TravelApplication.kt` → AppCheck initialization

**A.3 JaCoCo Threshold Enforce [S]**
- Mevcut JaCoCo convention plugin'e `violationRules` ekle → %70 minimum
- CI'da `jacocoTestCoverageVerification` task'ı çalıştır

**A.4 Hardcoded String Audit [M]**
- 68 string resource mevcut; kalan hardcoded Türkçe string'leri tespit et
- Eksikleri `strings.xml`'e taşı, `stringResource(R.string.xxx)` kullan

---

### Sprint B: Test Coverage + Quality [P1]

**B.1 Integration Tests [L]**
- Room DAO integration testleri (in-memory DB)
- Ktor client mock server testleri

**B.2 UI Tests (androidTest) [L]**
- `androidTest` dizinlerini oluştur
- Compose UI test: ExploreScreen, PlannerScreen, TranslatorScreen, GalleryScreen
- Navigation testi

**B.3 @Preview Fonksiyonları [M]**
- Tüm Screen + core/ui composable'lara Preview ekle
- Dark mode + Light mode variant
- Compact + Expanded variant

**B.4 @author Tag Tamamlama [S]**
- Eksik dosyalara `@author` tag ekle (42 dosyada var, geri kalanlar eksik)

**B.5 Kalan @Stable/@Immutable [S]**
- Composable parametrelerinde kullanılan model class'ları audit et
- Gerekli yerlere annotation ekle

---

### Sprint C: Performance + Gemini Nano [P2]

**C.1 Baseline Profiles [M]**
- `:app` modülüne BaselineProfileGenerator ekle
- Startup ve kritik yollar için profil üret

**C.2 Gemini Nano Gerçek Entegrasyon [XL]**
- FeatureAvailabilityChecker → gerçek cihaz Nano kontrolü (şu an `false` dönüyor)
- HybridAiTextService → Nano API stabilize olduğunda on-device first aktif et
- AI Edge / AiCore dependency ekle
- On-device model download yönetimi

**C.3 Foldable Support Genişletme [S]** *(opsiyonel)*
- Mevcut `currentWindowAdaptiveInfo()` yaklaşımı yeterli olabilir
- Gerekirse WindowInfoTracker + FoldingFeature ekle

---

## Bağımlılık Grafiği

```
Sprint A (Temel Eksikler) ─── bağımsız, hemen başlanabilir
  ├── A.1 WindowSizeClass → Sprint B.3 Preview'larda kullanılabilir
  ├── A.2 Firebase App Check → bağımsız
  ├── A.3 JaCoCo Threshold → Sprint B testleri sonrası anlamlı
  └── A.4 String Audit → bağımsız

Sprint B (Test + Quality) ─── Sprint A ile paralel başlanabilir
  ├── B.1 Integration Tests → bağımsız
  ├── B.2 UI Tests → bağımsız
  ├── B.3 @Preview → A.1 sonrası daha kapsamlı
  ├── B.4 @author → bağımsız
  └── B.5 @Stable → bağımsız

Sprint C (Performance + Nano) ─── en son
  ├── C.1 Baseline Profiles → Sprint B sonrası
  ├── C.2 Gemini Nano → API stabilizasyonuna bağlı (harici bağımlılık)
  └── C.3 Foldable → opsiyonel
```

## Tamamlanan Sprint Maddeleri (Referans)

Aşağıdaki maddeler önceki planda "yapılacak" olarak listelenmişti, artık **tamamlandı**:

| Madde | Önceki Durum | Şimdiki Durum |
|-------|-------------|---------------|
| Navigation 3 geçişi | ⚠️ Nav2 | ✅ NavDisplay + user-owned back stack |
| Snackbar implementasyonu (7 TODO) | ❌ | ✅ 4 Route'ta tam implemente |
| GeminiNanoClient / HybridAi / FeatureChecker | ❌ | ⚠️ Dosyalar var, cloud-only çalışıyor |
| Maps Compose entegrasyonu | ❌ | ✅ GoogleMap + Marker + Polyline |
| PlannerViewModelTest | ❌ | ✅ 11 test |
| GenerateTravelPlanUseCaseTest | ❌ | ✅ 3 test |
| Translation history (DB + UI) | ❌ | ✅ Entity + DAO + UI |
| TranslatorViewModelTest | ❌ | ✅ 9 test |
| Gerçek image generation | ❌ | ✅ Firebase AI |
| ImageDetail ekranı | ❌ | ✅ BottomSheet + DetailPane |
| Share functionality | ❌ | ✅ GalleryEffect.ShareImage |
| Image persistence (DB) | ❌ | ✅ GeneratedImageEntity + DAO |
| GalleryViewModelTest | ❌ | ✅ 5 test |
| Gemini Live API | ❌ | ✅ VoiceAssistantService + AudioRecorder |
| VoiceAssistantSheet | ❌ | ✅ ModalBottomSheet |
| ListDetailPaneScaffold | ❌ | ✅ Explore + Gallery |
| Predictive back | ❌ | ✅ enableOnBackInvokedCallback |
| Deep links | ❌ | ✅ 2 intent-filter |
| State restoration (rememberSaveable) | ⚠️ | ✅ PlannerScreen + NavigationState |
| LeakCanary | ❌ | ✅ debugImplementation v2.14 |
| @Immutable annotations | ❌ | ✅ City, Place, DayPlan, TravelPlan, UserPreference |
| JaCoCo convention plugin | ❌ | ✅ Plugin var, CI'da rapor üretiliyor |

## Verification

Her sprint sonunda:
1. `./gradlew assembleDebug` başarılı
2. `./gradlew testDebugUnitTest` başarılı
3. `./gradlew detekt` hata yok
4. `./gradlew jacocoTestReport` rapor üretimi
5. Manuel test: ilgili feature'ı cihazda çalıştır
6. Sprint B sonrası: `./gradlew jacocoTestCoverageVerification` → %70+ doğrula
