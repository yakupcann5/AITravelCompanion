package com.travel.companion.core.data.repository

import android.content.Context
import com.travel.companion.core.common.di.IoDispatcher
import com.travel.companion.core.data.mapper.toDomain
import com.travel.companion.core.data.mapper.toEntity
import com.travel.companion.core.database.dao.GeneratedImageDao
import com.travel.companion.core.model.GalleryImage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Offline-first uretilen gorsel repository'si.
 * Dosya kaydetme ve veritabani islemlerini kapsar.
 *
 * @author Yakup Can
 * @date 2026-03-31
 */
@Singleton
class OfflineFirstGeneratedImageRepository @Inject constructor(
    private val generatedImageDao: GeneratedImageDao,
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : GeneratedImageRepository {

    override fun getAllImages(): Flow<List<GalleryImage>> =
        generatedImageDao.getAllImages().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun saveGeneratedImage(prompt: String, imageBytes: ByteArray) = withContext(ioDispatcher) {
        val imageUrl = saveImageToFile(imageBytes)
        val image = GalleryImage(
            id = UUID.randomUUID().toString(),
            prompt = prompt,
            imageUrl = imageUrl,
        )
        generatedImageDao.upsertImage(image.toEntity())
    }

    override suspend fun upsertImage(image: GalleryImage) = withContext(ioDispatcher) {
        generatedImageDao.upsertImage(image.toEntity())
    }

    override suspend fun updateFavorite(id: String, isFavorite: Boolean) = withContext(ioDispatcher) {
        generatedImageDao.updateFavorite(id, isFavorite)
    }

    override suspend fun deleteImage(id: String) = withContext(ioDispatcher) {
        generatedImageDao.deleteImage(id)
    }

    private fun saveImageToFile(bytes: ByteArray): String {
        val dir = File(context.filesDir, "generated_images").apply { mkdirs() }
        val file = File(dir, "${UUID.randomUUID()}.jpg")
        file.writeBytes(bytes)
        return file.toURI().toString()
    }
}
