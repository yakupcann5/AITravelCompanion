package com.travel.companion.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Gezilecek yer ag katmani modeli.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Serializable
data class NetworkPlace(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("category") val category: String = "attraction",
    @SerialName("estimated_time") val estimatedTime: String = "",
    @SerialName("rating") val rating: Float = 0f,
)
