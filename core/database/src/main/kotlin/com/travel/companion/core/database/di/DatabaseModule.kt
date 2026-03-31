package com.travel.companion.core.database.di

import android.content.Context
import androidx.room.Room
import com.travel.companion.core.database.TravelDatabase
import com.travel.companion.core.database.dao.CityDao
import com.travel.companion.core.database.dao.GeneratedImageDao
import com.travel.companion.core.database.dao.TranslationHistoryDao
import com.travel.companion.core.database.dao.TravelPlanDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Veritabani Hilt DI modulu.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TravelDatabase =
        Room.databaseBuilder(
            context,
            TravelDatabase::class.java,
            "travel_companion_db",
        ).fallbackToDestructiveMigration(dropAllTables = false).build()

    @Provides
    fun provideCityDao(database: TravelDatabase): CityDao = database.cityDao()

    @Provides
    fun provideTravelPlanDao(database: TravelDatabase): TravelPlanDao = database.travelPlanDao()

    @Provides
    fun provideTranslationHistoryDao(database: TravelDatabase): TranslationHistoryDao =
        database.translationHistoryDao()

    @Provides
    fun provideGeneratedImageDao(database: TravelDatabase): GeneratedImageDao =
        database.generatedImageDao()
}
