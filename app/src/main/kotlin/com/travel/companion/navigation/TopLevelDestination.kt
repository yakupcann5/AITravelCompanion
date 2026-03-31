package com.travel.companion.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.travel.companion.core.model.ExploreRoute
import com.travel.companion.core.model.GalleryRoute
import com.travel.companion.core.model.PlannerRoute
import com.travel.companion.core.model.TranslatorRoute

/**
 * Alt navigasyon cubugu ust seviye destinasyonlari.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
enum class TopLevelDestination(
    val icon: ImageVector,
    val label: String,
    val route: NavKey,
) {
    EXPLORE(
        icon = Icons.Default.Explore,
        label = "Kesfet",
        route = ExploreRoute,
    ),
    PLANNER(
        icon = Icons.Default.Map,
        label = "Planla",
        route = PlannerRoute,
    ),
    TRANSLATOR(
        icon = Icons.Default.Translate,
        label = "Ceviri",
        route = TranslatorRoute,
    ),
    GALLERY(
        icon = Icons.Default.PhotoLibrary,
        label = "Galeri",
        route = GalleryRoute,
    ),
}
