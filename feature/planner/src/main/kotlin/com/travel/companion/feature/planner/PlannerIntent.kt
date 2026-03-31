package com.travel.companion.feature.planner

import com.travel.companion.core.model.Budget

/**
 * Planlayici ekrani kullanici aksiyonlari.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
sealed interface PlannerIntent {
    data class SetCityName(val cityName: String) : PlannerIntent
    data class SetBudget(val budget: Budget) : PlannerIntent
    data class ToggleInterest(val interest: String) : PlannerIntent
    data class SetDuration(val days: Int) : PlannerIntent
    data object GeneratePlan : PlannerIntent
    data class SelectDay(val dayIndex: Int) : PlannerIntent
    data object SavePlan : PlannerIntent
    data object ShowPreferences : PlannerIntent
    data object HidePreferences : PlannerIntent
    data object ShowVoiceSheet : PlannerIntent
    data object HideVoiceSheet : PlannerIntent
    data object StartVoice : PlannerIntent
    data object StopVoice : PlannerIntent
}
