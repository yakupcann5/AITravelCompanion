package com.travel.companion.core.ai.fake

import com.travel.companion.core.ai.AiImageService
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * AI Image servisi fake implementasyonu.
 * Nano Banana / Imagen entegrasyonu tamamlanana kadar kullanilir.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
class FakeAiImageService @Inject constructor() : AiImageService {

    override suspend fun generateImage(prompt: String): Result<ByteArray> {
        delay(3000L)
        // Gercek image byte'lari yerine bos array dondur
        return Result.success(ByteArray(0))
    }

    override suspend fun isAvailable(): Boolean = true
}
