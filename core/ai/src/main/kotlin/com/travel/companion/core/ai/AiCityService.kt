package com.travel.companion.core.ai

import com.travel.companion.core.model.City

/**
 * Sehir arama AI servisi.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
interface AiCityService {
    suspend fun searchCity(cityName: String): Result<City>
}
