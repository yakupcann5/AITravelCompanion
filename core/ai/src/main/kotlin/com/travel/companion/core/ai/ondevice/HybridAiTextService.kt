package com.travel.companion.core.ai.ondevice

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content
import com.travel.companion.core.ai.AiTextService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Hybrid AI Text servisi.
 * On-device (Gemini Nano) mevcutsa onu, degilse cloud (Gemini Flash) kullanir.
 *
 * Simdilik cloud-only. firebase-ai-ondevice API stabilize olunca
 * InferenceMode.PREFER_ON_DEVICE ile hybrid moda gecilecek.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Singleton
class HybridAiTextService @Inject constructor(
    private val availabilityChecker: FeatureAvailabilityChecker,
) : AiTextService {

    private val cloudModel by lazy {
        Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.5-flash")
    }

    // TODO: firebase-ai-ondevice stabilize olunca:
    // private val hybridModel by lazy {
    //     Firebase.ai(backend = GenerativeBackend.googleAI())
    //         .generativeModel(
    //             modelName = "gemini-2.5-flash",
    //             onDeviceConfig = OnDeviceConfig(mode = InferenceMode.PREFER_ON_DEVICE),
    //         )
    // }

    override suspend fun summarize(text: String): Result<String> = runCatching {
        val model = getPreferredModel()
        val response = model.generateContent(
            content {
                text("Asagidaki metni Turkce olarak 2-3 cumleyle ozetle:\n\n$text")
            },
        )
        response.text ?: error("AI bos yanit dondu")
    }.onFailure { Log.e(TAG, "Summarize failed", it) }

    override suspend fun translate(text: String, targetLang: String): Result<String> = runCatching {
        val model = getPreferredModel()
        val response = model.generateContent(
            content {
                text("Asagidaki metni $targetLang diline cevir. Sadece ceviriyi don:\n\n$text")
            },
        )
        response.text ?: error("AI bos yanit dondu")
    }.onFailure { Log.e(TAG, "Translate failed", it) }

    override suspend fun rewrite(text: String, tone: String): Result<String> = runCatching {
        val model = getPreferredModel()
        val response = model.generateContent(
            content {
                text("Asagidaki metni $tone tonunda yeniden yaz. Sadece yeniden yazilmis metni don:\n\n$text")
            },
        )
        response.text ?: error("AI bos yanit dondu")
    }.onFailure { Log.e(TAG, "Rewrite failed", it) }

    private suspend fun getPreferredModel() = if (availabilityChecker.isOnDeviceAvailable()) {
        Log.d(TAG, "On-device model kullaniliyor")
        cloudModel // TODO: hybridModel ile degistir (on-device API stabilize olunca)
    } else {
        cloudModel
    }

    override fun isAvailable(): Flow<Boolean> = flow {
        emit(availabilityChecker.isOnDeviceAvailable())
    }

    companion object {
        private const val TAG = "HybridAiText"
    }
}
