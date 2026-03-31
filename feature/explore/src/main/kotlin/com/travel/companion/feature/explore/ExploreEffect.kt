package com.travel.companion.feature.explore

import com.travel.companion.core.common.UiText

/**
 * Kesfet ekrani tek seferlik olaylar.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
sealed interface ExploreEffect {
    data class NavigateToPlanner(val cityId: String) : ExploreEffect
    data class ShowSnackbar(val message: UiText) : ExploreEffect
}
