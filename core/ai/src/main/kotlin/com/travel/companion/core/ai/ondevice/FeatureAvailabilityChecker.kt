package com.travel.companion.core.ai.ondevice

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Cihazda Gemini Nano mevcut mu kontrol eder.
 * firebase-ai-ondevice API stabilize olunca gercek implementasyona gecilecek.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Singleton
class FeatureAvailabilityChecker @Inject constructor() {

    suspend fun isOnDeviceAvailable(): Boolean {
        // TODO: firebase-ai-ondevice stabilize olunca:
        // val status = FirebaseAIOnDevice.checkStatus()
        // return status == OnDeviceModelStatus.AVAILABLE
        Log.d(TAG, "On-device AI henuz desteklenmiyor, cloud kullanilacak")
        return false
    }

    fun observeStatus(): Flow<OnDeviceStatus> = flowOf(OnDeviceStatus.Unavailable)

    companion object {
        private const val TAG = "FeatureAvailability"
    }
}

sealed interface OnDeviceStatus {
    data object Ready : OnDeviceStatus
    data object Downloading : OnDeviceStatus
    data object Downloadable : OnDeviceStatus
    data object Unavailable : OnDeviceStatus
}
