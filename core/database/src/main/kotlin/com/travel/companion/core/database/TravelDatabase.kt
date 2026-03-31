package com.travel.companion.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.travel.companion.core.database.dao.CityDao
import com.travel.companion.core.database.dao.GeneratedImageDao
import com.travel.companion.core.database.dao.TranslationHistoryDao
import com.travel.companion.core.database.dao.TravelPlanDao
import com.travel.companion.core.database.entity.CityEntity
import com.travel.companion.core.database.entity.GeneratedImageEntity
import com.travel.companion.core.database.entity.TranslationHistoryEntity
import com.travel.companion.core.database.entity.TravelPlanEntity

/**
 * Uygulama Room veritabani tanimlamasi.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Database(
    entities = [
        CityEntity::class,
        TravelPlanEntity::class,
        TranslationHistoryEntity::class,
        GeneratedImageEntity::class,
    ],
    version = 4,
    exportSchema = true,
)
abstract class TravelDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun travelPlanDao(): TravelPlanDao
    abstract fun translationHistoryDao(): TranslationHistoryDao
    abstract fun generatedImageDao(): GeneratedImageDao
}
