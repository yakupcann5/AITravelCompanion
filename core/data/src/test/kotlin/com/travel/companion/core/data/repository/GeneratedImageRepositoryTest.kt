package com.travel.companion.core.data.repository

/**
 * [OfflineFirstGeneratedImageRepository] birim testleri.
 *
 * @author Yakup Can
 * @date 2026-03-31
 */
import android.content.Context
import android.net.Uri
import app.cash.turbine.test
import com.travel.companion.core.database.dao.GeneratedImageDao
import com.travel.companion.core.database.entity.GeneratedImageEntity
import com.travel.companion.core.model.GalleryImage
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
class GeneratedImageRepositoryTest {

    private lateinit var dao: GeneratedImageDao
    private lateinit var context: Context
    private lateinit var tempDir: File
    private lateinit var repository: OfflineFirstGeneratedImageRepository
    private val imagesFlow = MutableStateFlow(sampleEntities())

    @Before
    fun setup() {
        tempDir = createTempDir()
        context = mockk()
        every { context.filesDir } returns tempDir
        dao = mockk(relaxed = true)
        every { dao.getAllImages() } returns imagesFlow
        repository = OfflineFirstGeneratedImageRepository(
            generatedImageDao = dao,
            context = context,
            ioDispatcher = UnconfinedTestDispatcher(),
        )
    }

    @Test
    fun `getAllImages emits mapped domain models`() = runTest {
        repository.getAllImages().test {
            val images = awaitItem()
            assertEquals(2, images.size)
            assertEquals("img-1", images[0].id)
            assertEquals("Istanbul sunset", images[0].prompt)
            assertFalse(images[0].isFavorite)
        }
    }

    @Test
    fun `getAllImages emits empty list when dao is empty`() = runTest {
        imagesFlow.value = emptyList()

        repository.getAllImages().test {
            assertTrue(awaitItem().isEmpty())
        }
    }

    @Test
    fun `getAllImages reflects dao flow updates reactively`() = runTest {
        repository.getAllImages().test {
            assertEquals(2, awaitItem().size)

            imagesFlow.value = sampleEntities() + GeneratedImageEntity("img-3", "Tokyo", "https://example.com/3.jpg")
            assertEquals(3, awaitItem().size)
        }
    }

    @Test
    fun `saveGeneratedImage saves bytes to file and upserts entity`() = runTest {
        val bytes = byteArrayOf(1, 2, 3, 4)
        val entitySlot = slot<GeneratedImageEntity>()
        coEvery { dao.upsertImage(capture(entitySlot)) } returns Unit

        repository.saveGeneratedImage("Istanbul at night", bytes)

        assertTrue(entitySlot.captured.imageUrl.startsWith("file:///"))
        assertEquals("Istanbul at night", entitySlot.captured.prompt)
        assertTrue(entitySlot.captured.id.isNotBlank())
    }

    @Test
    fun `saveGeneratedImage creates image file in generated_images dir`() = runTest {
        val bytes = byteArrayOf(10, 20, 30)

        repository.saveGeneratedImage("Paris sunset", bytes)

        val generatedDir = File(tempDir, "generated_images")
        assertTrue(generatedDir.exists())
        assertTrue(generatedDir.listFiles()?.isNotEmpty() == true)
    }

    @Test
    fun `upsertImage maps domain model to entity and calls dao`() = runTest {
        val image = GalleryImage(
            id = "new-1",
            prompt = "Paris Eiffel Tower",
            imageUrl = "https://example.com/paris.jpg",
            isFavorite = false,
            createdAt = 9000L,
        )
        val entitySlot = slot<GeneratedImageEntity>()
        coEvery { dao.upsertImage(capture(entitySlot)) } returns Unit

        repository.upsertImage(image)

        assertEquals("new-1", entitySlot.captured.id)
        assertEquals("Paris Eiffel Tower", entitySlot.captured.prompt)
        assertEquals("https://example.com/paris.jpg", entitySlot.captured.imageUrl)
        assertFalse(entitySlot.captured.isFavorite)
        assertEquals(9000L, entitySlot.captured.createdAt)
    }

    @Test
    fun `updateFavorite delegates id and flag to dao`() = runTest {
        repository.updateFavorite("img-1", true)

        coVerify(exactly = 1) { dao.updateFavorite("img-1", true) }
    }

    @Test
    fun `updateFavorite can unset favorite`() = runTest {
        repository.updateFavorite("img-2", false)

        coVerify(exactly = 1) { dao.updateFavorite("img-2", false) }
    }

    @Test
    fun `deleteImage delegates id to dao`() = runTest {
        repository.deleteImage("img-1")

        coVerify(exactly = 1) { dao.deleteImage("img-1") }
    }

    @Test
    fun `deleteImage does not call upsertImage`() = runTest {
        repository.deleteImage("img-1")

        coVerify(exactly = 0) { dao.upsertImage(any()) }
    }

    private fun sampleEntities() = listOf(
        GeneratedImageEntity("img-1", "Istanbul sunset", "https://example.com/1.jpg"),
        GeneratedImageEntity("img-2", "Paris Eiffel", "https://example.com/2.jpg"),
    )
}
