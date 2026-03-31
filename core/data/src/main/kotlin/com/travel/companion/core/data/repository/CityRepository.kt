package com.travel.companion.core.data.repository

import com.travel.companion.core.model.City
import kotlinx.coroutines.flow.Flow

/**
 * Sehir veri erisim katmani.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
interface CityRepository {
    fun getPopularCities(): Flow<List<City>>
    fun getRecentSearches(): Flow<List<City>>
    fun getCity(id: String): Flow<City?>
    fun searchCitiesLocal(query: String): Flow<List<City>>
    suspend fun searchCityByName(name: String): City?
    suspend fun insertCityFromAi(city: City)
    suspend fun prepopulate()
}
