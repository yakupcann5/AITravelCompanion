package com.travel.companion.core.ai

/**
 * AI gorsel uretim servisi abstraction layer.
 * Nano Banana ve Imagen implementasyonlari bu interface'i kullanir.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
interface AiImageService {
    suspend fun generateImage(prompt: String): Result<ByteArray>
    suspend fun isAvailable(): Boolean
}
