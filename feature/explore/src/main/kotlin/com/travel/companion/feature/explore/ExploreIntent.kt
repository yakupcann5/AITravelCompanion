package com.travel.companion.feature.explore

/**
 * Kesfet ekrani kullanici aksiyonlari.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
sealed interface ExploreIntent {
    data object LoadCities : ExploreIntent
    data class SearchQueryChanged(val query: String) : ExploreIntent
    data object SubmitSearch : ExploreIntent
    data class CityClicked(val cityId: String) : ExploreIntent
    data class ShowCityDetail(val cityId: String) : ExploreIntent
    data object DismissCityDetail : ExploreIntent
}
