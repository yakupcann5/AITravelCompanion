package com.travel.companion.feature.planner

import com.travel.companion.core.common.UiText
import com.travel.companion.core.model.Budget
import com.travel.companion.core.model.TravelPlan

/**
 * Planlayici ekrani UI durumu.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
data class PlannerState(
    val cityName: String = "",
    val budget: Budget = Budget.MEDIUM,
    val selectedInterests: List<String> = emptyList(),
    val durationDays: Int = 3,
    val travelPlan: TravelPlan? = null,
    val selectedDayIndex: Int = 0,
    val isGenerating: Boolean = false,
    val isSaving: Boolean = false,
    val showPreferences: Boolean = true,
    val error: UiText? = null,
    val showVoiceSheet: Boolean = false,
    val isVoiceActive: Boolean = false,
    val voiceTranscript: String = "",
) {
    val hasPlan: Boolean get() = travelPlan != null
    val selectedDay get() = travelPlan?.days?.getOrNull(selectedDayIndex)
    val canGenerate: Boolean get() = cityName.isNotBlank() && durationDays > 0
}

val availableInterests = listOf(
    "Tarih", "Kultur", "Yemek", "Doga", "Sanat",
    "Alisveris", "Gece Hayati", "Mimari", "Muzeler", "Plaj",
)
