package com.travel.companion.core.ai.voice

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Fake VoiceAssistantService. Gemini Live API entegrasyonu
 * tamamlanana kadar kullanilir.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
class FakeVoiceAssistantService @Inject constructor() : VoiceAssistantService {

    override fun startSession(): Flow<VoiceEvent> = flow {
        emit(VoiceEvent.SessionStarted)
        delay(1000)
        emit(VoiceEvent.Response("Merhaba! Size nasil yardimci olabilirim?"))
    }

    override suspend fun sendAudio(audioData: ByteArray) {
        // Gemini Live API entegrasyonu sonrasi implementasyon
    }

    override suspend fun sendText(text: String) {
        // Gemini Live API entegrasyonu sonrasi implementasyon
    }

    override suspend fun endSession() {
        // Gemini Live API entegrasyonu sonrasi implementasyon
    }

    override fun isAvailable(): Boolean = false
}
