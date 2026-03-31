package com.travel.companion.core.data.mapper

import com.travel.companion.core.database.entity.TranslationHistoryEntity
import com.travel.companion.core.model.TranslationHistoryItem

/**
 * TranslationHistory entity-domain donusturucu.
 *
 * @author Yakup Can
 * @date 2026-03-31
 */
fun TranslationHistoryEntity.toDomain(): TranslationHistoryItem = TranslationHistoryItem(
    sourceText = sourceText,
    targetText = targetText,
    sourceLang = sourceLang,
    targetLang = targetLang,
    timestamp = timestamp,
)

fun TranslationHistoryItem.toEntity(): TranslationHistoryEntity = TranslationHistoryEntity(
    id = 0L,
    sourceText = sourceText,
    targetText = targetText,
    sourceLang = sourceLang,
    targetLang = targetLang,
    timestamp = timestamp,
)
