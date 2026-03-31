package com.travel.companion.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Ceviri gecmisi Room veritabani entity'si.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Entity(tableName = "translation_history")
data class TranslationHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sourceText: String,
    val targetText: String,
    val sourceLang: String,
    val targetLang: String,
    val timestamp: Long,
)
