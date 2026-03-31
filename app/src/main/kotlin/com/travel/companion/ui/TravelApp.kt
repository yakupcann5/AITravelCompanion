package com.travel.companion.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.travel.companion.core.model.ExploreRoute
import com.travel.companion.core.model.GalleryRoute
import com.travel.companion.core.model.PlannerRoute
import com.travel.companion.core.model.TranslatorRoute
import com.travel.companion.navigation.Navigator
import com.travel.companion.navigation.TopLevelDestination
import com.travel.companion.navigation.TravelNavDisplay
import com.travel.companion.navigation.rememberNavigationState
import com.travel.companion.navigation.toEntries

/**
 * Kok Compose ekrani. NavigationSuiteScaffold ile alt navigasyonu barindirir.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Composable
fun TravelApp(modifier: Modifier = Modifier) {
    val topLevelRoutes = remember {
        setOf(ExploreRoute, PlannerRoute, TranslatorRoute, GalleryRoute)
    }
    val navigationState = rememberNavigationState(
        startRoute = ExploreRoute,
        topLevelRoutes = topLevelRoutes,
    )
    val navigator = remember { Navigator(navigationState) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            TopLevelDestination.entries.forEach { destination ->
                val selected = destination.route == navigationState.topLevelRoute

                item(
                    selected = selected,
                    onClick = { navigator.navigate(destination.route) },
                    icon = { Icon(destination.icon, contentDescription = destination.label) },
                    label = { Text(destination.label) },
                )
            }
        },
        modifier = modifier,
    ) {
        TravelNavDisplay(
            navigationState = navigationState,
            navigator = navigator,
        )
    }
}
