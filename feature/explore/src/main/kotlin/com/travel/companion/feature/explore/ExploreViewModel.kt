package com.travel.companion.feature.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.companion.core.common.UiText
import com.travel.companion.core.data.repository.CityRepository
import com.travel.companion.feature.explore.usecase.GetPopularCitiesUseCase
import com.travel.companion.feature.explore.usecase.GetRecentSearchesUseCase
import com.travel.companion.feature.explore.usecase.SearchCityWithAiUseCase
import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Kesfet ekrani MVI ViewModel.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getPopularCitiesUseCase: GetPopularCitiesUseCase,
    private val getRecentSearchesUseCase: GetRecentSearchesUseCase,
    private val searchCityWithAiUseCase: SearchCityWithAiUseCase,
    private val cityRepository: CityRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ExploreState())
    val state: StateFlow<ExploreState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ExploreEffect>(replay = 0)
    val effect: SharedFlow<ExploreEffect> = _effect.asSharedFlow()

    init {
        onIntent(ExploreIntent.LoadCities)
    }

    fun onIntent(intent: ExploreIntent) {
        when (intent) {
            is ExploreIntent.LoadCities -> loadCities()
            is ExploreIntent.SearchQueryChanged -> reduce {
                it.copy(searchQuery = intent.query, searchResult = null)
            }
            is ExploreIntent.SubmitSearch -> submitSearch()
            is ExploreIntent.CityClicked -> emitEffect(ExploreEffect.NavigateToPlanner(intent.cityId))
            is ExploreIntent.ShowCityDetail -> reduce { it.copy(detailCityId = intent.cityId) }
            is ExploreIntent.DismissCityDetail -> reduce { it.copy(detailCityId = null) }
        }
    }

    private fun loadCities() {
        viewModelScope.launch {
            try {
                reduce { it.copy(isLoading = true) }
                cityRepository.prepopulate()
                reduce { it.copy(isLoading = false) }
            } catch (e: Exception) {
                Log.e("ExploreViewModel", "prepopulate failed", e)
                reduce { it.copy(isLoading = false, error = e.toUiText()) }
            }
        }

        viewModelScope.launch {
            getPopularCitiesUseCase()
                .catch { e ->
                    Log.e("ExploreViewModel", "getPopularCities failed", e)
                    reduce { it.copy(error = e.toUiText()) }
                }
                .collect { cities ->
                    reduce { it.copy(popularCities = cities) }
                }
        }

        viewModelScope.launch {
            getRecentSearchesUseCase()
                .catch { e ->
                    Log.e("ExploreViewModel", "getRecentSearches failed", e)
                    reduce { it.copy(error = e.toUiText()) }
                }
                .collect { cities ->
                    reduce { it.copy(recentSearches = cities) }
                }
        }
    }

    private fun submitSearch() {
        val query = _state.value.searchQuery.trim()
        if (query.isBlank()) return

        viewModelScope.launch {
            reduce { it.copy(isSearching = true, error = null, searchResult = null) }
            searchCityWithAiUseCase(query)
                .onSuccess { city ->
                    reduce { it.copy(isSearching = false, searchResult = city) }
                }
                .onFailure { exception ->
                    Log.e("ExploreViewModel", "AI search failed", exception)
                    val uiText = exception.toUiText()
                    reduce { it.copy(isSearching = false, error = uiText) }
                    emitEffect(ExploreEffect.ShowSnackbar(uiText))
                }
        }
    }

    private fun reduce(transform: (ExploreState) -> ExploreState) {
        _state.update(transform)
    }

    private fun emitEffect(effect: ExploreEffect) {
        viewModelScope.launch { _effect.emit(effect) }
    }
}

private fun Throwable.toUiText(): UiText {
    val msg = this.message.orEmpty()
    return when {
        "quota" in msg.lowercase() -> UiText.StringResource(R.string.explore_error_quota)
        "network" in msg.lowercase() || this is java.net.UnknownHostException ->
            UiText.StringResource(R.string.explore_error_network)
        "timeout" in msg.lowercase() -> UiText.StringResource(R.string.explore_error_timeout)
        else -> UiText.StringResource(R.string.explore_error_generic)
    }
}
