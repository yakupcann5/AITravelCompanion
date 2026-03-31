package com.travel.companion.core.ai.voice

import kotlinx.coroutines.flow.Flow

/**
 * Sesli asistan servisi abstraction layer.
 * Gemini Live API ile bidirectional voice interaction saglar.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
interface VoiceAssistantService {
    fun startSession(): Flow<VoiceEvent>
    suspend fun sendAudio(audioData: ByteArray)
    suspend fun sendText(text: String)
    suspend fun endSession()
    fun isAvailable(): Boolean
}

sealed interface VoiceEvent {
    data class Transcript(val text: String, val isFinal: Boolean) : VoiceEvent
    data class Response(val text: String) : VoiceEvent
    data object SessionStarted : VoiceEvent
    data object SessionEnded : VoiceEvent
    data class Error(val message: String) : VoiceEvent
}
