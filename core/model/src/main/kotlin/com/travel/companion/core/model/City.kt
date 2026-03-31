package com.travel.companion.core.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

/**
 * Sehir domain modeli.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Immutable
@Serializable
data class City(
    val id: String,
    val name: String,
    val country: String,
    val description: String,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double,
    val tags: List<String> = emptyList(),
)
