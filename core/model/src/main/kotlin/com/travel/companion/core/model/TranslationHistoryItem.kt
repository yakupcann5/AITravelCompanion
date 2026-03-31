package com.travel.companion.core.model

import androidx.compose.runtime.Immutable

/**
 * Ceviri gecmisi domain modeli.
 *
 * @author Yakup Can
 * @date 2026-03-31
 */
@Immutable
data class TranslationHistoryItem(
    val sourceText: String,
    val targetText: String,
    val sourceLang: String,
    val targetLang: String,
    val timestamp: Long,
)
