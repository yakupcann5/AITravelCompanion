package com.travel.companion.core.ai.fake

import com.travel.companion.core.ai.AiCityService
import com.travel.companion.core.model.City
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Sehir arama AI servisi fake implementasyonu.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Singleton
class FakeAiCityService @Inject constructor() : AiCityService {

    override suspend fun searchCity(cityName: String): Result<City> {
        delay(1500L)
        val id = cityName.trim().lowercase().replace(" ", "-")
        return Result.success(
            City(
                id = id,
                name = cityName.trim().replaceFirstChar { it.uppercase() },
                country = "",
                description = "$cityName hakkinda bilgi yukleniyor...",
                imageUrl = "",
                latitude = 0.0,
                longitude = 0.0,
                tags = listOf("seyahat"),
            ),
        )
    }
}
