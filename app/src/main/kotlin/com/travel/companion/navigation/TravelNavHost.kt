package com.travel.companion.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.travel.companion.core.model.ExploreRoute
import com.travel.companion.core.model.GalleryRoute
import com.travel.companion.core.model.PlannerDetailRoute
import com.travel.companion.core.model.PlannerRoute
import com.travel.companion.core.model.TranslatorRoute
import com.travel.companion.feature.explore.ExploreRoute as ExploreScreen
import com.travel.companion.feature.gallery.GalleryRoute as GalleryScreen
import com.travel.companion.feature.planner.PlannerRoute as PlannerScreen
import com.travel.companion.feature.translator.TranslatorRoute as TranslatorScreen

/**
 * Navigation 3 NavDisplay ile ekran route eslestirmelerini tanimlar.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Composable
fun TravelNavDisplay(
    navigationState: NavigationState,
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    val provider = entryProvider {
        entry<ExploreRoute> {
            ExploreScreen(
                onNavigateToPlanner = { cityId ->
                    navigator.navigate(PlannerDetailRoute(cityId = cityId))
                },
            )
        }

        entry<PlannerRoute> {
            PlannerScreen(
                onPlanSaved = { navigator.goBack() },
            )
        }

        entry<PlannerDetailRoute> {
            PlannerScreen(
                onPlanSaved = { navigator.goBack() },
            )
        }

        entry<TranslatorRoute> {
            TranslatorScreen()
        }

        entry<GalleryRoute> {
            GalleryScreen()
        }
    }

    NavDisplay(
        entries = navigationState.toEntries(provider),
        onBack = { navigator.goBack() },
        modifier = modifier,
    )
}
