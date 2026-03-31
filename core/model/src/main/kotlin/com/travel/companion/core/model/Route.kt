package com.travel.companion.core.model

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Uygulama navigation route key tanimlamalari.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Serializable
data object ExploreRoute : NavKey

@Serializable
data object PlannerRoute : NavKey

@Serializable
data class PlannerDetailRoute(val cityId: String) : NavKey

@Serializable
data object TranslatorRoute : NavKey

@Serializable
data object GalleryRoute : NavKey
