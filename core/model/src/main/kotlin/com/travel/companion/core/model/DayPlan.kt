package com.travel.companion.core.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

/**
 * Gunluk gezi plani ve seyahat plani domain modelleri.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Immutable
@Serializable
data class DayPlan(
    val dayIndex: Int,
    val title: String,
    val places: List<Place> = emptyList(),
)

@Immutable
@Serializable
data class TravelPlan(
    val id: String,
    val cityId: String,
    val cityName: String,
    val days: List<DayPlan> = emptyList(),
    val budget: Budget = Budget.MEDIUM,
    val interests: List<String> = emptyList(),
    val createdAt: Long = 0L,
)

@Serializable
enum class Budget {
    LOW, MEDIUM, HIGH, LUXURY
}
