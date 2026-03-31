package com.travel.companion.feature.translator.usecase

/**
 * [TranslateTextUseCase] birim testleri.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
import com.travel.companion.core.ai.AiTextService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class TranslateTextUseCaseTest {

    @Test fun `returns translated text when service succeeds`() = runTest {
        val useCase = TranslateTextUseCase(FakeAiTextService(Result.success("Hello")))
        val result = useCase("Merhaba", "en")
        assertTrue(result.isSuccess)
        assertEquals("Hello", result.getOrNull())
    }

    @Test fun `returns failure when service fails`() = runTest {
        val useCase = TranslateTextUseCase(FakeAiTextService(Result.failure(RuntimeException("err"))))
        val result = useCase("text", "en")
        assertTrue(result.isFailure)
    }

    @Test fun `returns failure on timeout`() = runTest {
        val useCase = TranslateTextUseCase(FakeAiTextService(Result.failure(Exception("timeout"))))
        assertTrue(useCase("test", "de").isFailure)
    }

    @Test fun `passes empty text unchanged`() = runTest {
        val useCase = TranslateTextUseCase(FakeAiTextService(Result.success("")))
        assertEquals("", useCase("", "es").getOrNull())
    }
}

private class FakeAiTextService(private val result: Result<String>) : AiTextService {
    override suspend fun summarize(text: String) = Result.success("")
    override suspend fun translate(text: String, targetLang: String) = result
    override suspend fun rewrite(text: String, tone: String) = Result.success("")
    override fun isAvailable(): Flow<Boolean> = flowOf(true)
}
