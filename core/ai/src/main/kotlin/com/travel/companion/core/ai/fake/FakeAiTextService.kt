package com.travel.companion.core.ai.fake

import com.travel.companion.core.ai.AiTextService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

/**
 * AI Text servisi fake implementasyonu.
 * Gemini Nano / Firebase AI Logic entegrasyonu tamamlanana kadar kullanilir.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
class FakeAiTextService @Inject constructor() : AiTextService {

    override suspend fun summarize(text: String): Result<String> {
        delay(1000L)
        val summary = if (text.length > 200) {
            text.take(200) + "..."
        } else {
            text
        }
        return Result.success("Ozet: $summary")
    }

    override suspend fun translate(text: String, targetLang: String): Result<String> {
        delay(1500L)
        return Result.success("[Ceviri ($targetLang)]: $text")
    }

    override suspend fun rewrite(text: String, tone: String): Result<String> {
        delay(1000L)
        return Result.success("[$tone ton]: $text")
    }

    override fun isAvailable(): Flow<Boolean> = flowOf(true)
}
