package com.travel.companion.core.ai.firebase

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.ImagenGenerationConfig
import com.google.firebase.ai.type.PublicPreviewAPI
import com.travel.companion.core.ai.AiImageService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Firebase AI ile gorsel uretim servisi.
 * Imagen 3 modeli kullanarak prompt'tan gorsel uretir.
 *
 * @author Yakup Can
 * @date 2026-03-31
 */
@OptIn(PublicPreviewAPI::class)
@Singleton
class FirebaseAiImageService @Inject constructor() : AiImageService {

    private val imagenModel by lazy {
        Firebase.ai(backend = GenerativeBackend.googleAI())
            .imagenModel(
                modelName = "imagen-3.0-generate-002",
                generationConfig = ImagenGenerationConfig(numberOfImages = 1),
            )
    }

    override suspend fun generateImage(prompt: String): Result<ByteArray> = runCatching {
        val response = imagenModel.generateImages(
            "Guzel bir seyahat kartpostal gorseli olustur: $prompt",
        )
        val image = response.images.firstOrNull()
            ?: error("Imagen gorsel uretemedi — bos yanit dondu")
        if (image.data.isEmpty()) error("Gorsel verisi bos dondu")
        image.data
    }

    override suspend fun isAvailable(): Boolean = true
}
