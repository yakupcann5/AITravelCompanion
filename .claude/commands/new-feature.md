# New Feature Module Command

Yeni bir MVI feature modülü scaffold'u oluştur.

## 1. Kullanıcıya Sor

- Feature ismi nedir? (örn: explore, planner, translator, gallery)
- Kısa açıklama nedir?
- Navigation parametreleri var mı? (örn: cityId: String)

## 2. Oluşturulacak Dosyalar

Cevaplara göre şu dosyaları oluştur:

```
feature/[name]/
├── api/
│   ├── build.gradle.kts
│   └── src/main/kotlin/com/travel/feature/[name]/api/
│       └── [Name]Navigation.kt          # Route definition + navigation keys
│
└── impl/
    ├── build.gradle.kts
    └── src/main/kotlin/com/travel/feature/[name]/impl/
        ├── [Name]Intent.kt              # sealed interface
        ├── [Name]State.kt               # data class
        ├── [Name]Effect.kt              # sealed interface
        ├── [Name]ViewModel.kt           # @HiltViewModel + reduce
        ├── [Name]Screen.kt              # @Composable (state + onIntent)
        ├── [Name]Route.kt               # hiltViewModel + effect collection
        └── di/
            └── [Name]Module.kt          # Hilt bindings
```

## 3. Template İçerikleri

### api/[Name]Navigation.kt
```kotlin
@Serializable
data class [Name]Route(/* navigation params */)
```

### impl/[Name]Intent.kt
```kotlin
sealed interface [Name]Intent {
    data object Load : [Name]Intent
    // Ekrana özel intent'ler
}
```

### impl/[Name]State.kt
```kotlin
data class [Name]State(
    val isLoading: Boolean = false,
    val error: String? = null
    // Ekrana özel state field'ları
)
```

### impl/[Name]Effect.kt
```kotlin
sealed interface [Name]Effect {
    data class ShowSnackbar(val message: String) : [Name]Effect
    // Ekrana özel effect'ler
}
```

### impl/[Name]ViewModel.kt
MVI ViewModel template: _state (MutableStateFlow), _effect (MutableSharedFlow), onIntent(), reduce()

### impl/[Name]Screen.kt
Stateless composable: state + onIntent parametreleri, when block ile Loading/Error/Content

### impl/[Name]Route.kt
hiltViewModel() + collectAsStateWithLifecycle() + LaunchedEffect effect collection

## 4. Ayrıca

- settings.gradle.kts'e modülleri ekle
- app/build.gradle.kts'e dependency ekle
- Navigation 3 entryProvider'a yeni entry ekle
- Test dosyası oluştur: [Name]ViewModelTest.kt

## 5. Onay Al

Dosya listesini göster ve onayla. Onaydan sonra oluştur ve `./gradlew assembleDebug` ile doğrula.
