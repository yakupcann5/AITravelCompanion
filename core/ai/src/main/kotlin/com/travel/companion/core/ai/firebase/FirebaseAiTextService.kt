package com.travel.companion.core.ai.firebase

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content
import com.travel.companion.core.ai.AiTextService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Metin isleme Firebase AI servisi.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Singleton
class FirebaseAiTextService @Inject constructor() : AiTextService {

    private val model by lazy {
        Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.5-flash")
    }

    override suspend fun summarize(text: String): Result<String> = runCatching {
        val response = model.generateContent(
            content {
                text("Asagidaki metni Turkce olarak 2-3 cumleyle ozetle:\n\n$text")
            },
        )
        response.text ?: error("AI bos yanit dondu")
    }

    override suspend fun translate(text: String, targetLang: String): Result<String> = runCatching {
        val response = model.generateContent(
            content {
                text("Asagidaki metni $targetLang diline cevir. Sadece ceviriyi don:\n\n$text")
            },
        )
        response.text ?: error("AI bos yanit dondu")
    }

    override suspend fun rewrite(text: String, tone: String): Result<String> = runCatching {
        val response = model.generateContent(
            content {
                text("Asagidaki metni $tone tonunda yeniden yaz. Sadece yeniden yazilmis metni don:\n\n$text")
            },
        )
        response.text ?: error("AI bos yanit dondu")
    }

    override fun isAvailable(): Flow<Boolean> = flowOf(true)
}
