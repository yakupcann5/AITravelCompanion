# Proje 2: AI Travel Companion - Yol Haritasi

## Proje Ozeti

Yapay zeka destekli seyahat asistani uygulamasi. Kullanici gidecegi sehri secer, tercihlerini girer ve AI kisisellestirilmis gezi plani olusturur.

**Zorluk**: Yuksek | **Tahmini Sure**: 8-10 Hafta | **Mimari**: MVI + Feature-Based Modularization

---

## Onkosullar

| # | Gereksinim | Detay |
|---|-----------|-------|
| 1 | Android Studio Ladybug+ | Gemini in Studio destegi |
| 2 | Firebase projesi | AI Logic, App Check, Remote Config aktif |
| 3 | Google Maps API key | Maps Compose icin |
| 4 | Test cihaz | Gemini Nano icin Pixel 8/9 veya Galaxy S24/S25 |
| 5 | Min SDK 26, target SDK 35 | Nano icin SDK 31+ onerilir |
| 6 | Kotlin 2.0+, Compose BOM 2025.12.00+ | |

---

## Modul Yapisi

```
ai-travel-companion/
├── app/
├── feature/
│   ├── explore/     (api + impl)   # Sehir kesfi ve secimi
│   ├── planner/     (api + impl)   # AI gezi planlama
│   ├── translator/  (api + impl)   # Offline ceviri
│   └── gallery/     (api + impl)   # AI gorsel uretimi
├── core/
│   ├── model/          # Pure Kotlin domain models
│   ├── data/           # Repository implementations
│   ├── database/       # Room DB, DAOs, entities
│   ├── network/        # Ktor client, API models
│   ├── ai/             # AI servis abstraction layer
│   ├── ui/             # Shared composables
│   ├── designsystem/   # Theme, Material 3 Expressive
│   ├── common/         # Dispatchers, utils
│   └── testing/        # Test utilities
└── build-logic/        # Convention plugins
```

---

## Faz 0 — Proje Iskeleti (Hafta 1)

**Hedef**: Build alir, bos ekranlar arasinda navigate edebilen iskelet.

### Gun 1-2: Proje Setup
- [ ] build-logic/ convention plugins (android-library, android-feature, compose, hilt)
- [ ] libs.versions.toml (version catalog)
- [ ] settings.gradle.kts (tum moduller register)
- [ ] .editorconfig, detekt/ktlint config
- [ ] CI: GitHub Actions build + lint

### Gun 3-4: Core Moduller
- [ ] :core:model -> City, DayPlan, Place, Route, UserPreference data class'lari
- [ ] :core:common -> Dispatchers, Result wrapper, extension functions
- [ ] :core:designsystem -> TravelTheme, Material 3 Expressive, Dynamic Color
- [ ] :core:ui -> Shared composables (LoadingIndicator, ErrorScreen, CityCard)

### Gun 5: Navigation Iskeleti
- [ ] :app -> MainActivity, TravelApp composable
- [ ] Navigation 3 setup (NavDisplay, user-owned back stack)
- [ ] Adaptive navigation: BottomNav (compact), NavigationRail (expanded)
- [ ] WindowSizeClass altyapisi

**Cikti**: `./gradlew assembleDebug` basarili, 4 bos tab arasi gecis calisir.

---

## Faz 1 — Data Layer + Explore (Hafta 2-3)

**Hedef**: Sehir verisi akar, arama calisir, offline storage hazir.

### Hafta 2: Data Layer
- [ ] :core:database -> Room DB, Entity, DAO, TypeConverter
- [ ] :core:network -> Ktor Client, API models, model mapping
- [ ] :core:data -> CityRepository, TravelPlanRepository, UserPreferencesRepository
- [ ] Hilt modules (@Binds)

### Hafta 3: Explore Feature
- [ ] :feature:explore MVI (ExploreIntent, ExploreState, ExploreEffect)
- [ ] ExploreViewModel + ExploreScreen (SearchBar, Carousel, LazyStaggeredGrid)
- [ ] Tests: ExploreViewModelTest, CityRepositoryTest

**Cikti**: Sehir listesi, arama, Planner'a navigate.

---

## Faz 2 — AI Core + Planner (Hafta 4-6)

**Hedef**: AI gezi plani uretimi, haritada rota.

### Hafta 4: AI Abstraction Layer
- [ ] :core:ai interfaces (AiTextService, AiPlannerService, AiImageService)
- [ ] ondevice/ GeminiNanoClient, FeatureAvailabilityChecker
- [ ] cloud/ FirebaseAiClient, function calling JSON parse
- [ ] HybridAiService (on-device first, cloud fallback)

### Hafta 5: Planner Feature
- [ ] :feature:planner MVI (PlannerIntent, PlannerState, PlannerEffect)
- [ ] PlannerViewModel (Gemini Flash function calling)
- [ ] PlannerScreen (PreferencesSheet, ItineraryView, Adaptive layout)
- [ ] Use Cases: GenerateTravelPlanUseCase, SaveTravelPlanUseCase

### Hafta 6: Maps Entegrasyonu
- [ ] Maps Compose (Marker, Polyline, InfoWindow)
- [ ] Planner embed: Compact Tab, Expanded side-by-side
- [ ] Tests: PlannerViewModelTest, GenerateTravelPlanUseCaseTest

**Cikti**: Sehir sec -> tercih gir -> AI plan uret -> haritada gor -> kaydet.

---

## Faz 3 — Translator + Gallery (Hafta 7-8)

### Hafta 7: Translator
- [ ] :feature:translator MVI + TranslatorViewModel (Gemini Nano offline)
- [ ] TranslatorScreen (dil secici, source/target, gecmis)
- [ ] TranslateTextUseCase + tests

### Hafta 8: Gallery
- [ ] :feature:gallery MVI + GalleryViewModel (Nano Banana)
- [ ] GalleryScreen (PromptInput, StaggeredGrid, ImageDetail, Share)
- [ ] Coil 3 + tests

---

## Faz 4 — Voice + Adaptive Polish (Hafta 9)

- [ ] Gemini Live API (VoiceAssistantService, AudioRecorder)
- [ ] Planner FAB -> VoiceAssistantSheet
- [ ] ListDetailPaneScaffold tum feature'larda
- [ ] Foldable, predictive back, deep link, state restoration

---

## Faz 5 — Test + QA + CI/CD (Hafta 10)

- [ ] Unit + Integration + UI test tamamlama (%70+)
- [ ] GitHub Actions CI/CD (Build, Lint, Test, Coverage gate)
- [ ] Security checklist (API keys, App Check, ProGuard)
- [ ] Performance (Baseline Profiles, recomposition audit, LeakCanary)

---

## Gantt Ozeti

```
Hafta  1 ████░░░░░░░░░░░░░░░░  Faz 0: Iskelet + Navigation
Hafta  2 ░░░░████░░░░░░░░░░░░  Faz 1: Data Layer
Hafta  3 ░░░░░░░░████░░░░░░░░  Faz 1: Explore Feature
Hafta  4 ░░░░░░░░░░░░████░░░░  Faz 2: AI Core Layer
Hafta  5 ░░░░░░░░░░░░░░░░████  Faz 2: Planner Feature
Hafta  6 ░░░░░░░░░░░░░░░░████  Faz 2: Maps Entegrasyonu
Hafta  7 ░░░░░░░░░░░░████░░░░  Faz 3: Translator
Hafta  8 ░░░░░░░░░░░░░░░░████  Faz 3: Gallery + Image Gen
Hafta  9 ░░░░░░░░████░░░░░░░░  Faz 4: Voice + Adaptive Polish
Hafta 10 ░░░░████░░░░░░░░░░░░  Faz 5: Test + QA + CI/CD
```

---

## Risk ve Bagimliliklari

| Risk | Etki | Onlem |
|------|------|-------|
| Gemini Nano cihaz destegi sinirli | Offline ozellikler calismiyor | HybridAiService fallback |
| Navigation 3 yeni | Beklenmedik buglar | Nav 2 fallback plani |
| Maps Compose lisans maliyeti | Butce asimi | OSM alternatif |
| Gemini Live API beta | API degisebilir | Abstraction layer |
| Nano Banana gorsel kalitesi | Beklenti karsilanmaz | Imagen fallback |
