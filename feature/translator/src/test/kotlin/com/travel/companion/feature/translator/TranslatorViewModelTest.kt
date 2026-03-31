package com.travel.companion.feature.translator

/**
 * [TranslatorViewModel] birim testleri.
 *
 * @author Yakup Can
 * @date 2026-03-31
 */
import app.cash.turbine.test
import com.travel.companion.core.ai.AiTextService
import com.travel.companion.core.data.repository.TranslationHistoryRepository
import com.travel.companion.core.model.TranslationHistoryItem
import com.travel.companion.core.testing.MainDispatcherRule
import com.travel.companion.core.common.UiText
import com.travel.companion.feature.translator.usecase.TranslateTextUseCase
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TranslatorViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var fakeTranslateResult: Result<String> = Result.success("Merhaba")
    private lateinit var translationHistoryRepository: TranslationHistoryRepository
    private val historyFlow = MutableStateFlow(emptyList<TranslationHistoryItem>())

    private val fakeAiTextService = object : AiTextService {
        override suspend fun summarize(text: String) = Result.success("")
        override suspend fun translate(text: String, targetLang: String) = fakeTranslateResult
        override suspend fun rewrite(text: String, tone: String) = Result.success("")
        override fun isAvailable(): Flow<Boolean> = flowOf(true)
    }

    @Before
    fun setup() {
        translationHistoryRepository = mockk(relaxed = true)
        every { translationHistoryRepository.getRecentTranslations() } returns historyFlow
    }

    private fun createViewModel() = TranslatorViewModel(
        translateTextUseCase = TranslateTextUseCase(fakeAiTextService),
        translationHistoryRepository = translationHistoryRepository,
    )

    @Test fun `InputTextChanged updates sourceText`() = runTest {
        val vm = createViewModel()
        vm.onIntent(TranslatorIntent.InputTextChanged("Hello"))
        assertEquals("Hello", vm.state.value.sourceText)
    }

    @Test fun `SourceLangChanged updates sourceLang`() = runTest {
        val vm = createViewModel()
        vm.onIntent(TranslatorIntent.SourceLangChanged("de"))
        assertEquals("de", vm.state.value.sourceLang)
    }

    @Test fun `TargetLangChanged updates targetLang`() = runTest {
        val vm = createViewModel()
        vm.onIntent(TranslatorIntent.TargetLangChanged("fr"))
        assertEquals("fr", vm.state.value.targetLang)
    }

    @Test fun `SwapLanguages swaps langs and texts`() = runTest {
        fakeTranslateResult = Result.success("Merhaba")
        val vm = createViewModel()
        vm.onIntent(TranslatorIntent.SourceLangChanged("en"))
        vm.onIntent(TranslatorIntent.TargetLangChanged("tr"))
        vm.onIntent(TranslatorIntent.InputTextChanged("Hello"))
        vm.onIntent(TranslatorIntent.Translate)
        advanceUntilIdle()

        vm.onIntent(TranslatorIntent.SwapLanguages)
        assertEquals("tr", vm.state.value.sourceLang)
        assertEquals("en", vm.state.value.targetLang)
    }

    @Test fun `Translate with success updates targetText`() = runTest {
        fakeTranslateResult = Result.success("Merhaba")
        val vm = createViewModel()
        vm.onIntent(TranslatorIntent.InputTextChanged("Hello"))
        vm.onIntent(TranslatorIntent.Translate)
        advanceUntilIdle()

        assertEquals("Merhaba", vm.state.value.targetText)
        assertFalse(vm.state.value.isTranslating)
    }

    @Test fun `Translate with failure sets error`() = runTest {
        fakeTranslateResult = Result.failure(RuntimeException("Network error"))
        val vm = createViewModel()
        vm.onIntent(TranslatorIntent.InputTextChanged("Hello"))
        vm.onIntent(TranslatorIntent.Translate)
        advanceUntilIdle()

        assertNotNull(vm.state.value.error)
        assertFalse(vm.state.value.isTranslating)
    }

    @Test fun `Translate with failure sets DynamicString error when message present`() = runTest {
        fakeTranslateResult = Result.failure(RuntimeException("Network error"))
        val vm = createViewModel()
        vm.onIntent(TranslatorIntent.InputTextChanged("Hello"))
        vm.onIntent(TranslatorIntent.Translate)
        advanceUntilIdle()

        assertTrue(vm.state.value.error is UiText.DynamicString)
    }

    @Test fun `Translate with failure sets StringResource error when no message`() = runTest {
        fakeTranslateResult = Result.failure(RuntimeException())
        val vm = createViewModel()
        vm.onIntent(TranslatorIntent.InputTextChanged("Hello"))
        vm.onIntent(TranslatorIntent.Translate)
        advanceUntilIdle()

        assertTrue(vm.state.value.error is UiText.StringResource)
    }

    @Test fun `DismissError clears error state`() = runTest {
        fakeTranslateResult = Result.failure(RuntimeException("Network error"))
        val vm = createViewModel()
        vm.onIntent(TranslatorIntent.InputTextChanged("Hello"))
        vm.onIntent(TranslatorIntent.Translate)
        advanceUntilIdle()

        vm.onIntent(TranslatorIntent.DismissError)
        assertNull(vm.state.value.error)
    }

    @Test fun `Translate with failure emits ShowSnackbar effect`() = runTest {
        fakeTranslateResult = Result.failure(RuntimeException("Network error"))
        val vm = createViewModel()
        vm.onIntent(TranslatorIntent.InputTextChanged("Hello"))

        vm.effect.test {
            vm.onIntent(TranslatorIntent.Translate)
            advanceUntilIdle()
            assertTrue(awaitItem() is TranslatorEffect.ShowSnackbar)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test fun `Translate with blank text does nothing`() = runTest {
        val vm = createViewModel()
        vm.onIntent(TranslatorIntent.InputTextChanged("   "))
        vm.onIntent(TranslatorIntent.Translate)
        advanceUntilIdle()

        assertEquals("", vm.state.value.targetText)
        assertFalse(vm.state.value.isTranslating)
    }

    @Test fun `ClearAll resets state`() = runTest {
        fakeTranslateResult = Result.success("Merhaba")
        val vm = createViewModel()
        vm.onIntent(TranslatorIntent.InputTextChanged("Hello"))
        vm.onIntent(TranslatorIntent.Translate)
        advanceUntilIdle()

        vm.onIntent(TranslatorIntent.ClearAll)
        assertEquals("", vm.state.value.sourceText)
        assertEquals("", vm.state.value.targetText)
    }

    @Test fun `CopyResult emits CopiedToClipboard`() = runTest {
        val vm = createViewModel()
        vm.effect.test {
            vm.onIntent(TranslatorIntent.CopyResult)
            assertTrue(awaitItem() is TranslatorEffect.CopiedToClipboard)
        }
    }

    @Test fun `LoadHistory populates history from repository`() = runTest {
        historyFlow.value = listOf(
            TranslationHistoryItem("Hello", "Merhaba", "en", "tr", 1711152000000L),
        )
        val vm = createViewModel()
        advanceUntilIdle()

        assertEquals(1, vm.state.value.history.size)
        assertEquals("Hello", vm.state.value.history[0].sourceText)
    }

    @Test fun `ClearHistory delegates to repository`() = runTest {
        val vm = createViewModel()
        vm.onIntent(TranslatorIntent.ClearHistory)
        advanceUntilIdle()

        coVerify(exactly = 1) { translationHistoryRepository.clearHistory() }
    }

    @Test fun `SelectHistoryItem updates all state fields`() = runTest {
        val vm = createViewModel()
        vm.onIntent(
            TranslatorIntent.SelectHistoryItem(
                sourceText = "Hello",
                targetText = "Merhaba",
                sourceLang = "en",
                targetLang = "tr",
            ),
        )

        assertEquals("Hello", vm.state.value.sourceText)
        assertEquals("Merhaba", vm.state.value.targetText)
        assertEquals("en", vm.state.value.sourceLang)
        assertEquals("tr", vm.state.value.targetLang)
    }

    @Test fun `Translate success saves to repository`() = runTest {
        fakeTranslateResult = Result.success("Merhaba")
        val vm = createViewModel()
        vm.onIntent(TranslatorIntent.InputTextChanged("Hello"))
        vm.onIntent(TranslatorIntent.Translate)
        advanceUntilIdle()

        coVerify(exactly = 1) {
            translationHistoryRepository.insertTranslation(any())
        }
    }
}
