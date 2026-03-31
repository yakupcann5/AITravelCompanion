# Travel Companion — CLAUDE.md

Proje genelinde uyulması gereken kritik kurallar. Bu dosyadaki kuralları değiştirmeden önce lead'e danış.

## Kritik Kod Kuralları

### File URI Formatı
`OfflineFirstGeneratedImageRepository.saveImageToFile()` **mutlaka** `file.toURI().toString()` kullanmalıdır.

```kotlin
// DOĞRU — Coil 3 uyumlu, file:/// prefix üretir
return file.toURI().toString()

// YANLIŞ — Coil 3 bare path'i yükleyemez, AsyncImage boş görünür
return file.absolutePath
```

**Neden:** `AsyncImage(model = "/data/user/0/...")` → Coil 3 scheme-less URI parse eder → görsel yüklenmez.
**Regression test:** `GeneratedImageRepositoryTest` → `startsWith("file:///")` assertion ile korunuyor.

---

### Race Condition Koruması
`GalleryViewModel.loadImages()` içindeki `Mutex` + `hasPrepopulated` pattern **kaldırılmamalıdır**.

```kotlin
// Bu iki field zorunludur:
private val prepopulateMutex = Mutex()
private var hasPrepopulated = false
```

**Neden:** DB Flow collect ve prepopulateWithSamples() arasındaki race condition'ı önler. Kaldırılırsa boş galeri → çoklu prepopulate çağrısı oluşabilir.

---

### Firebase AI Imagen 3 Opt-In
`FirebaseAiImageService` **mutlaka** `@OptIn(PublicPreviewAPI::class)` annotation'ına sahip olmalıdır.

```kotlin
// DOĞRU
@OptIn(PublicPreviewAPI::class)
@Singleton
class FirebaseAiImageService @Inject constructor() : AiImageService {
```

**Neden:** `firebase-ai 16.2.0` — `imagenModel()` API `PublicPreviewAPI` annotation'ı gerektirir. Kaldırılırsa `core:ai` modülü compile edilemez.

---

### Effect Collection Pattern
Tüm Route dosyalarında effect collection **mutlaka** `ObserveAsEvents` composable ile yapılmalıdır.

```kotlin
// DOĞRU
ObserveAsEvents(viewModel.effect) { effect -> ... }

// YANLIŞ — config change'de effect'ler kaybolur
LaunchedEffect(Unit) { viewModel.effect.collect { ... } }
```

**Neden:** `repeatOnLifecycle(STARTED)` lifecycle-aware collection sağlar. Background'da effect'ler kuyruğa alınır, drop edilmez.

---

### Clean Architecture Sınırları
Feature modüller `core:database`'e **direkt bağımlı olmamalıdır**.

```
feature:* → core:data (Repository interface)
feature:* → core:model (Domain model)
feature:* ✗ core:database (DAO, Entity)
```

---

## Proje Teknoloji Stack'i

- **Language:** Kotlin 2.0+
- **UI:** Jetpack Compose (BOM 2025.05.01+), Material 3
- **Navigation:** Navigation 3 (NavDisplay, user-owned backstack)
- **DI:** Dagger Hilt (`@HiltViewModel`, `@AndroidEntryPoint`)
- **DB:** Room (KMP-ready)
- **Network:** Ktor Client (OkHttp engine)
- **Images:** Coil 3 (`AsyncImage`)
- **AI:** Firebase AI Logic (Gemini 2.5 Flash text, Imagen 3 image)
- **Pattern:** MVI (Intent/State/Effect) — Clean Architecture

## Test Standartları

- JUnit 4/5, MockK, Turbine (Flow)
- `MainDispatcherRule` her ViewModel testinde zorunlu
- `ViewModel`, `UseCase`, `Repository` coverage %70+
- `DismissError` / trivial intent'ler dahil tüm intent'ler test edilmeli
