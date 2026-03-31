package com.travel.companion.feature.explore

import com.travel.companion.core.common.UiText
import com.travel.companion.core.model.City

/**
 * Kesfet ekrani UI durumu.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
data class ExploreState(
    val popularCities: List<City> = emptyList(),
    val recentSearches: List<City> = emptyList(),
    val searchQuery: String = "",
    val searchResult: City? = null,
    val isLoading: Boolean = false,
    val isSearching: Boolean = false,
    val error: UiText? = null,
    val detailCityId: String? = null,
) {
    val detailCity: City?
        get() = detailCityId?.let { id ->
            (popularCities + recentSearches + listOfNotNull(searchResult))
                .firstOrNull { it.id == id }
        }

    val filteredPopularCities: List<City>
        get() = if (searchQuery.isBlank()) {
            popularCities
        } else {
            popularCities.filter { city ->
                city.name.contains(searchQuery, ignoreCase = true) ||
                    city.country.contains(searchQuery, ignoreCase = true) ||
                    city.tags.any { it.contains(searchQuery, ignoreCase = true) }
            }
        }
}
