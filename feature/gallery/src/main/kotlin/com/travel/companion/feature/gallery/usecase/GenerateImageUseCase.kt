package com.travel.companion.feature.gallery.usecase

import com.travel.companion.core.ai.AiImageService
import javax.inject.Inject

/**
 * AI gorsel uretimi use case.
 * Nano Banana / Imagen ile kartpostal uretir.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
class GenerateImageUseCase @Inject constructor(
    private val aiImageService: AiImageService,
) {
    suspend operator fun invoke(prompt: String): Result<ByteArray> =
        aiImageService.generateImage(prompt)
}
