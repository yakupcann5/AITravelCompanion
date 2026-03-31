package com.travel.companion.core.data.mapper

import com.travel.companion.core.database.entity.CityEntity
import com.travel.companion.core.model.City
import kotlinx.serialization.json.Json

/**
 * City entity-domain donusturucu.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
private val json = Json { ignoreUnknownKeys = true }

fun CityEntity.toDomain(): City = City(
    id = id,
    name = name,
    country = country,
    description = description,
    imageUrl = imageUrl,
    latitude = latitude,
    longitude = longitude,
    tags = runCatching { json.decodeFromString<List<String>>(tagsJson) }.getOrDefault(emptyList()),
)

fun City.toEntity(isPopular: Boolean = false, searchedAt: Long? = null): CityEntity = CityEntity(
    id = id,
    name = name,
    country = country,
    description = description,
    imageUrl = imageUrl,
    latitude = latitude,
    longitude = longitude,
    tagsJson = json.encodeToString(tags),
    isPopular = isPopular,
    searchedAt = searchedAt,
)
