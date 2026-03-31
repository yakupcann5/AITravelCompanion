package com.travel.companion.feature.gallery.usecase

/**
 * [GenerateImageUseCase] birim testleri.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
import com.travel.companion.core.ai.AiImageService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GenerateImageUseCaseTest {

    @Test fun `returns image bytes when service succeeds`() = runTest {
        val bytes = byteArrayOf(1, 2, 3)
        val useCase = GenerateImageUseCase(FakeAiImageService(Result.success(bytes)))
        val result = useCase("sunset")
        assertTrue(result.isSuccess)
        assertArrayEquals(bytes, result.getOrNull())
    }

    @Test fun `returns failure when service fails`() = runTest {
        val useCase = GenerateImageUseCase(FakeAiImageService(Result.failure(RuntimeException("err"))))
        assertTrue(useCase("test").isFailure)
    }

    @Test fun `returns failure on rate limit`() = runTest {
        val useCase = GenerateImageUseCase(FakeAiImageService(Result.failure(RuntimeException("rate limit"))))
        assertTrue(useCase("test").isFailure)
    }

    @Test fun `delegates prompt to service`() = runTest {
        val service = FakeAiImageService(Result.success(byteArrayOf()))
        val useCase = GenerateImageUseCase(service)
        useCase("my prompt")
        assertEquals("my prompt", service.lastPrompt)
    }
}

private class FakeAiImageService(private val result: Result<ByteArray>) : AiImageService {
    var lastPrompt: String? = null
    override suspend fun generateImage(prompt: String): Result<ByteArray> {
        lastPrompt = prompt
        return result
    }
    override suspend fun isAvailable() = true
}
