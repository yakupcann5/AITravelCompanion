package com.travel.companion.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.travel.companion.core.database.entity.GeneratedImageEntity
import kotlinx.coroutines.flow.Flow

/**
 * Uretilen gorsel Room DAO interface'i.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Dao
interface GeneratedImageDao {

    @Query("SELECT * FROM generated_images ORDER BY createdAt DESC")
    fun getAllImages(): Flow<List<GeneratedImageEntity>>

    @Upsert
    suspend fun upsertImage(entity: GeneratedImageEntity)

    @Query("DELETE FROM generated_images WHERE id = :id")
    suspend fun deleteImage(id: String)

    @Query("UPDATE generated_images SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)
}
