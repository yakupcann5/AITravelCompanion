package com.travel.companion.core.ai.voice

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Android AudioRecord API ile ses kaydi yapar.
 * Flow tabanli chunk stream dondurur.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Singleton
class AudioRecorder @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun hasPermission(): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO,
        ) == PackageManager.PERMISSION_GRANTED

    fun startRecording(): Flow<ByteArray> = callbackFlow {
        if (!hasPermission()) {
            close(SecurityException("RECORD_AUDIO permission required"))
            return@callbackFlow
        }

        val bufferSize = AudioRecord.getMinBufferSize(
            SAMPLE_RATE,
            CHANNEL_CONFIG,
            AUDIO_FORMAT,
        )

        val audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            CHANNEL_CONFIG,
            AUDIO_FORMAT,
            bufferSize,
        )

        audioRecord.startRecording()
        Log.d(TAG, "Recording started")

        withContext(Dispatchers.IO) {
            val buffer = ByteArray(bufferSize)
            while (isActive) {
                val bytesRead = audioRecord.read(buffer, 0, bufferSize)
                if (bytesRead > 0) {
                    trySend(buffer.copyOf(bytesRead))
                }
            }
        }

        awaitClose {
            audioRecord.stop()
            audioRecord.release()
            Log.d(TAG, "Recording stopped")
        }
    }

    private companion object {
        const val TAG = "AudioRecorder"
        const val SAMPLE_RATE = 16000
        val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
        val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT
    }
}
