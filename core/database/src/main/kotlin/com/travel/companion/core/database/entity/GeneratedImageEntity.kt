package com.travel.companion.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Uretilen gorsel Room veritabani entity'si.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Entity(tableName = "generated_images")
data class GeneratedImageEntity(
    @PrimaryKey val id: String,
    val prompt: String,
    val imageUrl: String,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
)
