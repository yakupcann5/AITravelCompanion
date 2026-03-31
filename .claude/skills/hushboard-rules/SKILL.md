---
name: hushboard-rules
description: Hushboard projesinin CLAUDE.md'deki tum proje-ozel kurallarini tek bir yerde toplayan referans skill. Qualified name yasagi, logging, dokumantasyon, commit format, feature structure ve network config kurallarini icerir.
---

# Hushboard Project Rules Reference

Bu skill, Hushboard projesinin CLAUDE.md'de tanimlanan tum proje-ozel kurallarini icerir. Yeni kod yazarken veya review yaparken bu kurallara uy.

## 1. Qualified Name Yasagi (KRITIK)

**Asla qualified name kullanma, her zaman import kullan.**

```kotlin
// YANLIS
val dialog = android.app.AlertDialog.Builder(context)
val imm = context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
binding.textView.visibility = android.view.View.GONE

// DOGRU
import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

val dialog = AlertDialog.Builder(context)
val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
binding.textView.visibility = View.GONE
```

**Kapsam:** Class instantiation, constant erisimi, type annotations, generic type parameters, static method calls.
**Istisna:** Sadece `com.hushboard.R` resource sinifi icin qualified name kullanilabilir.

## 2. Logging Standards

**println() YASAK.** Sadece `android.util.Log` kullan.

```kotlin
import android.util.Log

companion object {
    private const val TAG = "ClassName"
}

Log.d(TAG, "Debug bilgisi")
Log.i(TAG, "Genel bilgi")
Log.w(TAG, "Uyari mesaji")
Log.e(TAG, "Hata: ${e.message}", e)
Log.wtf(TAG, "Kritik hata")
```

**Kurallar:** TAG = class ismi, hassas bilgi loglanmaz, error log'da stack trace ekle.

## 3. Dokumantasyon Formati

**Her YENI class'a zorunlu KDoc header:**
```kotlin
/**
 * [Amac aciklamasi - Turkce]
 *
 * @author Yakup Can
 * @date YYYY-MM-DD
 */
```

Karmasik logic icin Turkce yorum ekle. Self-explanatory koda yorum ekleme.

## 4. Commit Mesaj Formati

```
HB-XXX type(scope): kisa aciklama
```
Tipler: feat, fix, refactor, docs, test, chore, perf, ci, style
Commit mesajlari Ingilizce, kod yorumlari Turkce.

## 5. Feature Structure Convention

```
ui/[feature]/
├── [Feature]Fragment.kt
├── [Feature]ViewModel.kt
├── adapter/
│   └── [Feature]Adapter.kt
└── dialog/
    └── [Feature]Dialog.kt
```

## 6. Network Configuration

```
Base URL:    http://212.68.50.112:30000/
Auth:        x-auth header (bearer token)
Internal:    x-internal-key header
Timeouts:    30 seconds (read/connect)
```

## 7. Comment System (KRITIK)

**Comment = Post.** Ayri comment endpoint/model YOK.

| Islem | API |
|-------|-----|
| Yorum olusturma | `POST /api/v1/post` + `parentPostId` |
| Yorum guncelleme | `PUT /api/v1/post/{commentId}` |
| Yorum silme | `DELETE /api/v1/post/{commentId}` |
| Yorumlari getirme | `GET /api/v1/post/{postId}` → `replies` |
| Yorum begenme | `PUT /api/v1/post/{commentId}/like` |

Comment ID = Post ID. `CommentRepository` uses `PostServices`. `CommentServices` DEPRECATED.

## 8. Architecture Data Flow

```
Fragment → ViewModel → UseCase → Repository Interface → RepositoryImpl → Retrofit Service
```

## 9. State Management Pattern

```kotlin
// ViewModel
private val _uiState = MutableStateFlow<RequestState<T>>(RequestState.Loading)
val uiState: StateFlow<RequestState<T>> = _uiState.asStateFlow()

// Fragment
viewLifecycleOwner.lifecycleScope.launch {
    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.uiState.collect { state -> handleState(state) }
    }
}
```

## 10. Naming Conventions

| Tur | Format | Ornek |
|-----|--------|-------|
| Degiskenler | camelCase | `userName` |
| Siniflar | PascalCase | `UserRepository` |
| Sabitler | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT` |
| XML ID | snake_case | `btn_login` |
| Resource | snake_case | `fragment_login.xml` |

## 11. Yasak Listesi

- Optimistic UI, LiveData (yeni kod), Callback/Listener (async), AsyncTask
- findViewById, println(), Qualified name, notifyDataSetChanged(), GlobalScope
