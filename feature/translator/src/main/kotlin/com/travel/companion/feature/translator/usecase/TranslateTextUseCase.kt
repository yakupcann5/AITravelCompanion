package com.travel.companion.feature.translator.usecase

import com.travel.companion.core.ai.AiTextService
import javax.inject.Inject

/**
 * Metin cevirisi use case.
 * On-device (Nano) mevcutsa onu, degilse cloud fallback kullanir.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
class TranslateTextUseCase @Inject constructor(
    private val aiTextService: AiTextService,
) {
    suspend operator fun invoke(text: String, targetLang: String): Result<String> =
        aiTextService.translate(text, targetLang)
}
