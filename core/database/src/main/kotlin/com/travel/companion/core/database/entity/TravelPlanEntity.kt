package com.travel.companion.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Seyahat plani Room veritabani entity'si.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Entity(tableName = "travel_plans")
data class TravelPlanEntity(
    @PrimaryKey val id: String,
    val cityId: String,
    val cityName: String,
    val budget: String,
    val interests: String, // JSON array stored as string
    val daysJson: String, // JSON serialized DayPlan list
    val createdAt: Long,
)
