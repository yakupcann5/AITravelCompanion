package com.travel.companion.feature.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.travel.companion.core.common.UiText
import com.travel.companion.core.model.City
import com.travel.companion.core.ui.CityCard
import com.travel.companion.core.ui.LoadingIndicator
import com.travel.companion.core.ui.asString
import kotlinx.coroutines.launch

/**
 * Kesfet ekrani Compose UI.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Composable
internal fun ExploreScreen(
    state: ExploreState,
    onIntent: (ExploreIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        state.isLoading -> LoadingIndicator(modifier)
        else -> ExploreContent(state = state, onIntent = onIntent, modifier = modifier)
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun ExploreContent(
    state: ExploreState,
    onIntent: (ExploreIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowWidth = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
    val isExpanded = windowWidth == WindowWidthSizeClass.EXPANDED

    if (isExpanded) {
        ExpandedExploreLayout(state = state, onIntent = onIntent, modifier = modifier)
    } else {
        CityGrid(
            state = state,
            onIntent = onIntent,
            columns = if (windowWidth == WindowWidthSizeClass.COMPACT) 2 else 3,
            onCityClick = { cityId -> onIntent(ExploreIntent.CityClicked(cityId)) },
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun ExpandedExploreLayout(
    state: ExploreState,
    onIntent: (ExploreIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<String>()
    val scope = rememberCoroutineScope()

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                CityGrid(
                    state = state,
                    onIntent = onIntent,
                    columns = 3,
                    onCityClick = { cityId ->
                        onIntent(ExploreIntent.ShowCityDetail(cityId))
                        scope.launch {
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, cityId)
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        },
        detailPane = {
            AnimatedPane {
                CityDetailPane(
                    city = state.detailCity,
                    onNavigateToPlan = { cityId -> onIntent(ExploreIntent.CityClicked(cityId)) },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun CityGrid(
    state: ExploreState,
    onIntent: (ExploreIntent) -> Unit,
    columns: Int,
    onCityClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(columns),
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalItemSpacing = 12.dp,
    ) {
        headerSection()
        searchBarSection(state, onIntent)
        searchResultSection(state, onCityClick)
        errorSection(state.error)
        recentSearchesSection(state.recentSearches, onCityClick)
        popularCitiesSection(state.filteredPopularCities, onCityClick)
    }
}

private fun LazyStaggeredGridScope.headerSection() {
    item(span = StaggeredGridItemSpan.FullLine) {
        Text(
            text = stringResource(R.string.explore_title),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(vertical = 8.dp),
        )
    }
}

private fun LazyStaggeredGridScope.searchBarSection(
    state: ExploreState,
    onIntent: (ExploreIntent) -> Unit,
) {
    item(span = StaggeredGridItemSpan.FullLine) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { onIntent(ExploreIntent.SearchQueryChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.explore_search_placeholder)) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onIntent(ExploreIntent.SubmitSearch) }),
            trailingIcon = {
                if (state.isSearching) {
                    CircularProgressIndicator(Modifier.size(24.dp))
                } else {
                    IconButton(onClick = { onIntent(ExploreIntent.SubmitSearch) }) {
                        Icon(Icons.Default.Search, contentDescription = stringResource(R.string.explore_search_button_desc))
                    }
                }
            },
        )
    }
}

private fun LazyStaggeredGridScope.searchResultSection(
    state: ExploreState,
    onCityClick: (String) -> Unit,
) {
    val searchResult = state.searchResult ?: return

    item(span = StaggeredGridItemSpan.FullLine) {
        Text(
            text = stringResource(R.string.explore_search_result_title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
    item(span = StaggeredGridItemSpan.FullLine) {
        CityCard(
            city = searchResult,
            onClick = { onCityClick(searchResult.id) },
        )
    }
}

private fun LazyStaggeredGridScope.errorSection(error: UiText?) {
    if (error == null) return

    item(span = StaggeredGridItemSpan.FullLine) {
        Text(
            text = error.asString(),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 4.dp),
        )
    }
}

private fun LazyStaggeredGridScope.recentSearchesSection(
    recentSearches: List<City>,
    onCityClick: (String) -> Unit,
) {
    if (recentSearches.isEmpty()) return

    item(span = StaggeredGridItemSpan.FullLine) {
        Text(
            text = stringResource(R.string.explore_recent_searches_title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
    items(items = recentSearches, key = { it.id }) { city ->
        CityCard(city = city, onClick = { onCityClick(city.id) })
    }
}

private fun LazyStaggeredGridScope.popularCitiesSection(
    cities: List<City>,
    onCityClick: (String) -> Unit,
) {
    item(span = StaggeredGridItemSpan.FullLine) {
        Text(
            text = stringResource(R.string.explore_popular_cities_title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
    items(items = cities, key = { it.id }) { city ->
        CityCard(city = city, onClick = { onCityClick(city.id) })
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
private fun ExploreScreenPreview() {
    com.travel.companion.core.designsystem.theme.TravelCompanionTheme {
        ExploreScreen(
            state = ExploreState(
                popularCities = listOf(
                    City("1", "Istanbul", "Turkiye", "Tarihi sehir", "", 41.0, 28.9, listOf("Tarih")),
                    City("2", "Paris", "Fransa", "Isiklar sehri", "", 48.8, 2.3, listOf("Kultur")),
                ),
            ),
            onIntent = {},
        )
    }
}
