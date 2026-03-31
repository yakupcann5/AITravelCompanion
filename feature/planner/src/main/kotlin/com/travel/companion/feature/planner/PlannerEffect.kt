package com.travel.companion.feature.planner

import com.travel.companion.core.common.UiText

/**
 * Planlayici ekrani tek seferlik olaylar.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
sealed interface PlannerEffect {
    data object PlanSaved : PlannerEffect
    data class ShowSnackbar(val message: UiText) : PlannerEffect
}
