package com.travel.companion.core.data.prepopulate

import android.content.Context
import android.util.Log
import com.travel.companion.core.common.di.IoDispatcher
import com.travel.companion.core.data.mapper.toEntity
import com.travel.companion.core.database.dao.CityDao
import com.travel.companion.core.model.City
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Sehir verilerini assets'ten Room veritabanina yukleyen sinif.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Singleton
class CityPrepopulator @Inject constructor(
    private val cityDao: CityDao,
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun prepopulateIfNeeded() = withContext(ioDispatcher) {
        val count = cityDao.getPopularCityCount()
        if (count > 0) return@withContext

        runCatching {
            val jsonString = context.assets.open("cities.json").bufferedReader().use { it.readText() }
            val cities: List<City> = json.decodeFromString(jsonString)
            cityDao.upsertCities(cities.map { it.toEntity(isPopular = true) })
        }.onFailure { e ->
            Log.e("CityPrepopulator", "Failed to prepopulate cities", e)
        }
    }
}
