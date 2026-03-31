# New Class Template Command

Yeni bir Kotlin class oluşturmak istiyorum. Lütfen aşağıdaki adımları izle:

## 1. Kullanıcıya Sor

Önce bana şunları sor:
- Class ismi nedir?
- Class tipi nedir? (ViewModel / Repository / UseCase / Screen / Route / Intent / State / Effect / Entity / DAO / HiltModule / Custom)
- Hangi feature modülüne ait? (explore / planner / translator / gallery / core)
- Kısa açıklama nedir? (Class'ın amacı)

## 2. Template Hazırla

### MVI Feature Class'ları (Intent / State / Effect)

```kotlin
/**
 * [Kısa açıklama - Türkçe]
 *
 * @author Yakup Can
 * @date [Bugünün tarihi: YYYY-MM-DD]
 */

// Intent
sealed interface [Feature]Intent {
    data object Load : [Feature]Intent
    // TODO: Add intents
}

// State
data class [Feature]State(
    val isLoading: Boolean = false,
    val error: String? = null
    // TODO: Add state fields
)

// Effect
sealed interface [Feature]Effect {
    data class ShowSnackbar(val message: String) : [Feature]Effect
    // TODO: Add effects
}
```

### MVI ViewModel

```kotlin
/**
 * [Kısa açıklama - Türkçe]
 *
 * @author Yakup Can
 * @date [Bugünün tarihi: YYYY-MM-DD]
 */
@HiltViewModel
class [Feature]ViewModel @Inject constructor(
    private val useCase: [Feature]UseCase
) : ViewModel() {

    private val _state = MutableStateFlow([Feature]State())
    val state: StateFlow<[Feature]State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<[Feature]Effect>(replay = 0)
    val effect: SharedFlow<[Feature]Effect> = _effect.asSharedFlow()

    fun onIntent(intent: [Feature]Intent) {
        when (intent) {
            // TODO: Handle intents
        }
    }

    private fun reduce(transform: ([Feature]State) -> [Feature]State) {
        _state.update(transform)
    }
}
```

### Screen (Compose)

```kotlin
/**
 * [Kısa açıklama - Türkçe]
 *
 * @author Yakup Can
 * @date [Bugünün tarihi: YYYY-MM-DD]
 */
@Composable
internal fun [Feature]Screen(
    state: [Feature]State,
    onIntent: ([Feature]Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: Implement UI
}
```

### Route (Compose)

```kotlin
@Composable
internal fun [Feature]Route(
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: [Feature]ViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                // TODO: Handle effects
            }
        }
    }

    [Feature]Screen(state = state, onIntent = viewModel::onIntent, modifier = modifier)
}
```

### Diger tipler (Repository, UseCase, Entity, DAO)

Mevcut MVVM/Clean Architecture template'lerini kullan.

## 3. Standartlar

Template'i hazırlarken şunlara dikkat et:
- MVI pattern (Intent/State/Effect) — Proje 2 için
- Hilt DI annotations
- Compose only (XML yok)
- Navigation 3 uyumlu route'lar
- Bugünün tarihi (YYYY-MM-DD formatında)
- Immutable state (val only, data class)

## 4. Onay Al

Template'i göster ve şunu sor:
"Bu template'i onaylıyor musun? Değişiklik yapmak ister misin?"

Onaylandıktan sonra dosyayı oluştur.
