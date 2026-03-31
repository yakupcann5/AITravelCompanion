package com.travel.companion.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.travel.companion.core.database.entity.TranslationHistoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Ceviri gecmisi Room DAO interface'i.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Dao
interface TranslationHistoryDao {

    @Query("SELECT * FROM translation_history ORDER BY timestamp DESC LIMIT 20")
    fun getRecentTranslations(): Flow<List<TranslationHistoryEntity>>

    @Upsert
    suspend fun insertTranslation(entity: TranslationHistoryEntity)

    @Query("DELETE FROM translation_history")
    suspend fun clearHistory()
}
