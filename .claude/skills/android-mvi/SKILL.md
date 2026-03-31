---
name: android-mvi
description: MVI (Model-View-Intent) architecture pattern for Android Compose. Use when implementing screens with unidirectional data flow, complex state management, or debugging state issues.
---

# Android MVI Pattern

## When to Use
- Complex screens with many state combinations
- Compose projects (natural fit for unidirectional flow)
- Navigation 3 projects (user-owned back stack + immutable state)
- Teams needing strict state management discipline

## Structure

### Intent (sealed interface)
Represents ALL possible user/system actions for a screen.

```kotlin
sealed interface ExploreIntent {
    data object LoadCities : ExploreIntent
    data object Refresh : ExploreIntent
    data class SearchQueryChanged(val query: String) : ExploreIntent
    data class CityClicked(val cityId: String) : ExploreIntent
    data class FilterSelected(val filter: CityFilter) : ExploreIntent
    data object RetryAfterError : ExploreIntent
}
```

### State (data class)
Single immutable object representing the ENTIRE screen state.

```kotlin
data class ExploreState(
    val cities: List<City> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val activeFilter: CityFilter = CityFilter.ALL
) {
    // Derived state (computed, not stored)
    val filteredCities: List<City>
        get() = cities
            .filter { activeFilter.matches(it) }
            .filter { it.name.contains(searchQuery, ignoreCase = true) }

    val isEmpty: Boolean
        get() = !isLoading && cities.isEmpty() && error == null
}
```

### Effect (sealed interface)
One-shot events that should not survive configuration changes.

```kotlin
sealed interface ExploreEffect {
    data class NavigateToPlanner(val cityId: String) : ExploreEffect
    data class ShowSnackbar(val message: String) : ExploreEffect
    data object ScrollToTop : ExploreEffect
}
```

### ViewModel (MVI Container)

```kotlin
@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val refreshCitiesUseCase: RefreshCitiesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ExploreState())
    val state: StateFlow<ExploreState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ExploreEffect>(replay = 0)
    val effect: SharedFlow<ExploreEffect> = _effect.asSharedFlow()

    init { onIntent(ExploreIntent.LoadCities) }

    fun onIntent(intent: ExploreIntent) {
        when (intent) {
            is ExploreIntent.LoadCities -> loadCities()
            is ExploreIntent.Refresh -> refresh()
            is ExploreIntent.SearchQueryChanged -> reduce { it.copy(searchQuery = intent.query) }
            is ExploreIntent.CityClicked -> emitEffect(ExploreEffect.NavigateToPlanner(intent.cityId))
            is ExploreIntent.FilterSelected -> reduce { it.copy(activeFilter = intent.filter) }
            is ExploreIntent.RetryAfterError -> loadCities()
        }
    }

    private fun loadCities() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true, error = null) }
            getCitiesUseCase()
                .catch { e -> reduce { it.copy(isLoading = false, error = e.message) } }
                .collect { cities -> reduce { it.copy(isLoading = false, cities = cities) } }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            reduce { it.copy(isRefreshing = true) }
            runCatching { refreshCitiesUseCase() }
                .onFailure { e -> emitEffect(ExploreEffect.ShowSnackbar(e.message ?: "Refresh failed")) }
            reduce { it.copy(isRefreshing = false) }
        }
    }

    private fun reduce(transform: (ExploreState) -> ExploreState) {
        _state.update(transform)
    }

    private fun emitEffect(effect: ExploreEffect) {
        viewModelScope.launch { _effect.emit(effect) }
    }
}
```

## Comparison: MVI vs MVVM

| Aspect | MVVM | MVI |
|--------|------|-----|
| State | Multiple StateFlows | Single State object |
| Actions | Direct method calls | Sealed Intent class |
| State changes | Any method can update | Only reduce() updates |
| Debugging | Track multiple flows | Log single state stream |
| Boilerplate | Less | More (Intent/State/Effect) |
| Best for | Simple screens | Complex, stateful screens |

## Rules
- ONE State per screen (never multiple StateFlows)
- State is IMMUTABLE (data class, val only)
- ALL user actions map to Intent
- State changes ONLY through reduce()
- Effects for one-shot events (navigation, snackbar)
- Screen composable receives state + onIntent lambda (no ViewModel reference)
