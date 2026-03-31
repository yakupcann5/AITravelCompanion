package com.travel.companion.core.data.repository

/**
 * [OfflineFirstTranslationHistoryRepository] birim testleri.
 *
 * @author Yakup Can
 * @date 2026-03-31
 */
import app.cash.turbine.test
import com.travel.companion.core.database.dao.TranslationHistoryDao
import com.travel.companion.core.database.entity.TranslationHistoryEntity
import com.travel.companion.core.model.TranslationHistoryItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TranslationHistoryRepositoryTest {

    private lateinit var dao: TranslationHistoryDao
    private lateinit var repository: OfflineFirstTranslationHistoryRepository

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        repository = OfflineFirstTranslationHistoryRepository(
            translationHistoryDao = dao,
            ioDispatcher = UnconfinedTestDispatcher(),
        )
    }

    @Test
    fun `getRecentTranslations emits mapped domain models`() = runTest {
        val entities = listOf(
            TranslationHistoryEntity(1L, "Hello", "Merhaba", "en", "tr", 1000L),
            TranslationHistoryEntity(2L, "Good morning", "Günaydın", "en", "tr", 2000L),
        )
        every { dao.getRecentTranslations() } returns flowOf(entities)

        repository.getRecentTranslations().test {
            val items = awaitItem()
            assertEquals(2, items.size)
            assertEquals("Hello", items[0].sourceText)
            assertEquals("Merhaba", items[0].targetText)
            assertEquals("en", items[0].sourceLang)
            assertEquals("tr", items[0].targetLang)
            assertEquals(1000L, items[0].timestamp)
            awaitComplete()
        }
    }

    @Test
    fun `getRecentTranslations emits empty list when no history`() = runTest {
        every { dao.getRecentTranslations() } returns flowOf(emptyList())

        repository.getRecentTranslations().test {
            assertTrue(awaitItem().isEmpty())
            awaitComplete()
        }
    }

    @Test
    fun `insertTranslation maps item to entity and calls dao`() = runTest {
        val item = TranslationHistoryItem(
            sourceText = "Good night",
            targetText = "İyi geceler",
            sourceLang = "en",
            targetLang = "tr",
            timestamp = 3000L,
        )
        val entitySlot = slot<TranslationHistoryEntity>()
        coEvery { dao.insertTranslation(capture(entitySlot)) } returns Unit

        repository.insertTranslation(item)

        assertEquals("Good night", entitySlot.captured.sourceText)
        assertEquals("İyi geceler", entitySlot.captured.targetText)
        assertEquals("en", entitySlot.captured.sourceLang)
        assertEquals("tr", entitySlot.captured.targetLang)
        assertEquals(3000L, entitySlot.captured.timestamp)
    }

    @Test
    fun `insertTranslation preserves autoGenerate default id`() = runTest {
        val item = TranslationHistoryItem("Thanks", "Teşekkürler", "en", "tr", 5000L)
        val entitySlot = slot<TranslationHistoryEntity>()
        coEvery { dao.insertTranslation(capture(entitySlot)) } returns Unit

        repository.insertTranslation(item)

        assertEquals(0L, entitySlot.captured.id)
    }

    @Test
    fun `clearHistory delegates to dao`() = runTest {
        repository.clearHistory()

        coVerify(exactly = 1) { dao.clearHistory() }
    }

    @Test
    fun `clearHistory does not call insertTranslation`() = runTest {
        repository.clearHistory()

        coVerify(exactly = 0) { dao.insertTranslation(any()) }
    }
}
