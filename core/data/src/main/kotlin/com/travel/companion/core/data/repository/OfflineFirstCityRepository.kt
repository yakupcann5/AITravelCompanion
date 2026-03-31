package com.travel.companion.core.data.repository

import com.travel.companion.core.data.mapper.toDomain
import com.travel.companion.core.data.mapper.toEntity
import com.travel.companion.core.data.prepopulate.CityPrepopulator
import com.travel.companion.core.database.dao.CityDao
import com.travel.companion.core.model.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Sehir veri erisim katmani.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Singleton
class OfflineFirstCityRepository @Inject constructor(
    private val cityDao: CityDao,
    private val cityPrepopulator: CityPrepopulator,
) : CityRepository {

    override fun getPopularCities(): Flow<List<City>> =
        cityDao.getPopularCities().map { entities -> entities.map { it.toDomain() } }

    override fun getRecentSearches(): Flow<List<City>> =
        cityDao.getRecentSearches().map { entities -> entities.map { it.toDomain() } }

    override fun getCity(id: String): Flow<City?> =
        cityDao.getCity(id).map { it?.toDomain() }

    override fun searchCitiesLocal(query: String): Flow<List<City>> =
        cityDao.searchCities(query).map { entities -> entities.map { it.toDomain() } }

    override suspend fun searchCityByName(name: String): City? =
        cityDao.getCityByName(name)?.toDomain()

    override suspend fun insertCityFromAi(city: City) {
        cityDao.upsertCity(city.toEntity(searchedAt = System.currentTimeMillis()))
    }

    override suspend fun prepopulate() {
        cityPrepopulator.prepopulateIfNeeded()
    }
}
