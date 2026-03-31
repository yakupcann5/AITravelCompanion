package com.travel.companion.core.data.repository

import com.travel.companion.core.model.TranslationHistoryItem
import kotlinx.coroutines.flow.Flow

/**
 * Ceviri gecmisi repository sozlesmesi.
 *
 * @author Yakup Can
 * @date 2026-03-31
 */
interface TranslationHistoryRepository {
    fun getRecentTranslations(): Flow<List<TranslationHistoryItem>>
    suspend fun insertTranslation(item: TranslationHistoryItem)
    suspend fun clearHistory()
}
