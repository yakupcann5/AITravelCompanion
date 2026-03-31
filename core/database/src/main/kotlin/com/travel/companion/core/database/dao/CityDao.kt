package com.travel.companion.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.travel.companion.core.database.entity.CityEntity
import kotlinx.coroutines.flow.Flow

/**
 * Sehir Room DAO interface'i.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Dao
interface CityDao {

    @Query("SELECT * FROM cities WHERE isPopular = 1")
    fun getPopularCities(): Flow<List<CityEntity>>

    @Query("SELECT * FROM cities WHERE searchedAt IS NOT NULL ORDER BY searchedAt DESC LIMIT 10")
    fun getRecentSearches(): Flow<List<CityEntity>>

    @Query("SELECT * FROM cities WHERE id = :id")
    fun getCity(id: String): Flow<CityEntity?>

    @Query(
        "SELECT * FROM cities WHERE name LIKE '%' || :query || '%' " +
            "OR country LIKE '%' || :query || '%' COLLATE NOCASE",
    )
    fun searchCities(query: String): Flow<List<CityEntity>>

    @Query("SELECT * FROM cities WHERE LOWER(name) = LOWER(:name) LIMIT 1")
    suspend fun getCityByName(name: String): CityEntity?

    @Upsert
    suspend fun upsertCity(city: CityEntity)

    @Upsert
    suspend fun upsertCities(cities: List<CityEntity>)

    @Query("SELECT COUNT(*) FROM cities WHERE isPopular = 1")
    suspend fun getPopularCityCount(): Int
}
