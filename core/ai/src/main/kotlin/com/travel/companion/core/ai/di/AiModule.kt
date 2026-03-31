package com.travel.companion.core.ai.di

import com.travel.companion.core.ai.AiCityService
import com.travel.companion.core.ai.AiImageService
import com.travel.companion.core.ai.AiPlannerService
import com.travel.companion.core.ai.AiTextService
import com.travel.companion.core.ai.firebase.FirebaseAiCityService
import com.travel.companion.core.ai.firebase.FirebaseAiImageService
import com.travel.companion.core.ai.firebase.FirebaseAiPlannerService
import com.travel.companion.core.ai.ondevice.HybridAiTextService
import com.travel.companion.core.ai.voice.FakeVoiceAssistantService
import com.travel.companion.core.ai.voice.VoiceAssistantService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * AI servisleri Hilt DI modulu.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AiModule {

    @Binds
    abstract fun bindAiTextService(impl: HybridAiTextService): AiTextService

    @Binds
    abstract fun bindAiPlannerService(impl: FirebaseAiPlannerService): AiPlannerService

    @Binds
    abstract fun bindAiImageService(impl: FirebaseAiImageService): AiImageService

    @Binds
    abstract fun bindAiCityService(impl: FirebaseAiCityService): AiCityService

    @Binds
    abstract fun bindVoiceAssistantService(impl: FakeVoiceAssistantService): VoiceAssistantService
}
