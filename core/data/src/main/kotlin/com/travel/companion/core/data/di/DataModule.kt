package com.travel.companion.core.data.di

import com.travel.companion.core.data.repository.CityRepository
import com.travel.companion.core.data.repository.GeneratedImageRepository
import com.travel.companion.core.data.repository.OfflineFirstCityRepository
import com.travel.companion.core.data.repository.OfflineFirstGeneratedImageRepository
import com.travel.companion.core.data.repository.OfflineFirstTranslationHistoryRepository
import com.travel.companion.core.data.repository.OfflineFirstTravelPlanRepository
import com.travel.companion.core.data.repository.TranslationHistoryRepository
import com.travel.companion.core.data.repository.TravelPlanRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Veri katmani repository baglamalari Hilt DI modulu.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindCityRepository(impl: OfflineFirstCityRepository): CityRepository

    @Binds
    abstract fun bindTravelPlanRepository(impl: OfflineFirstTravelPlanRepository): TravelPlanRepository

    @Binds
    abstract fun bindTranslationHistoryRepository(
        impl: OfflineFirstTranslationHistoryRepository,
    ): TranslationHistoryRepository

    @Binds
    abstract fun bindGeneratedImageRepository(
        impl: OfflineFirstGeneratedImageRepository,
    ): GeneratedImageRepository
}
