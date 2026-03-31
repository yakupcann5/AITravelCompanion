package com.travel.companion.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Sehir Room veritabani entity'si.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey val id: String,
    val name: String,
    val country: String,
    val description: String,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double,
    val tagsJson: String = "[]",
    val isPopular: Boolean = false,
    val searchedAt: Long? = null,
)
