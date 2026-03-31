package com.travel.companion.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Sehir ag katmani modeli.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Serializable
data class NetworkCity(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("country") val country: String,
    @SerialName("description") val description: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("tags") val tags: List<String> = emptyList(),
)
