package com.travel.companion.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Seyahat plani ag katmani modeli.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Serializable
data class NetworkTravelPlan(
    @SerialName("id") val id: String,
    @SerialName("city_id") val cityId: String,
    @SerialName("city_name") val cityName: String,
    @SerialName("days") val days: List<NetworkDayPlan> = emptyList(),
    @SerialName("budget") val budget: String = "medium",
    @SerialName("interests") val interests: List<String> = emptyList(),
    @SerialName("created_at") val createdAt: Long = 0L,
)

@Serializable
data class NetworkDayPlan(
    @SerialName("day_index") val dayIndex: Int,
    @SerialName("title") val title: String,
    @SerialName("places") val places: List<NetworkPlace> = emptyList(),
)
