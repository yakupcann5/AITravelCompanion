package com.travel.companion.core.ai

import kotlinx.coroutines.flow.Flow

/**
 * AI metin servisi abstraction layer.
 * On-device (Gemini Nano) ve cloud (Firebase AI Logic) implementasyonlari bu interface'i kullanir.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
interface AiTextService {
    suspend fun summarize(text: String): Result<String>
    suspend fun translate(text: String, targetLang: String): Result<String>
    suspend fun rewrite(text: String, tone: String): Result<String>
    fun isAvailable(): Flow<Boolean>
}
