package com.travel.companion.core.data.repository

import com.travel.companion.core.model.GalleryImage
import kotlinx.coroutines.flow.Flow

/**
 * Uretilen gorsel repository sozlesmesi.
 *
 * @author Yakup Can
 * @date 2026-03-31
 */
interface GeneratedImageRepository {
    fun getAllImages(): Flow<List<GalleryImage>>
    suspend fun saveGeneratedImage(prompt: String, imageBytes: ByteArray)
    suspend fun upsertImage(image: GalleryImage)
    suspend fun updateFavorite(id: String, isFavorite: Boolean)
    suspend fun deleteImage(id: String)
}
