package com.travel.companion.core.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

/**
 * Gezilecek yer domain modeli.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Immutable
@Serializable
data class Place(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double,
    val category: PlaceCategory = PlaceCategory.ATTRACTION,
    val estimatedTime: String = "",
    val rating: Float = 0f,
)

@Serializable
enum class PlaceCategory {
    ATTRACTION, RESTAURANT, MUSEUM, PARK, SHOPPING, NIGHTLIFE, HOTEL
}
