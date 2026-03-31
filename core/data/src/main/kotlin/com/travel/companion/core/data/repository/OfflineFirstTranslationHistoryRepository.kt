package com.travel.companion.core.data.repository

import com.travel.companion.core.common.di.IoDispatcher
import com.travel.companion.core.data.mapper.toDomain
import com.travel.companion.core.data.mapper.toEntity
import com.travel.companion.core.database.dao.TranslationHistoryDao
import com.travel.companion.core.model.TranslationHistoryItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Offline-first ceviri gecmisi repository'si.
 *
 * @author Yakup Can
 * @date 2026-03-31
 */
@Singleton
class OfflineFirstTranslationHistoryRepository @Inject constructor(
    private val translationHistoryDao: TranslationHistoryDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : TranslationHistoryRepository {

    override fun getRecentTranslations(): Flow<List<TranslationHistoryItem>> =
        translationHistoryDao.getRecentTranslations().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun insertTranslation(item: TranslationHistoryItem) = withContext(ioDispatcher) {
        translationHistoryDao.insertTranslation(item.toEntity())
    }

    override suspend fun clearHistory() = withContext(ioDispatcher) {
        translationHistoryDao.clearHistory()
    }
}
