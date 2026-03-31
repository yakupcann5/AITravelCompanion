package com.travel.companion.feature.explore.usecase

import com.travel.companion.core.ai.AiCityService
import com.travel.companion.core.data.repository.CityRepository
import com.travel.companion.core.model.City
import javax.inject.Inject

/**
 * AI ile sehir arama is mantigi use case'i.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
class SearchCityWithAiUseCase @Inject constructor(
    private val cityRepository: CityRepository,
    private val aiCityService: AiCityService,
) {
    suspend operator fun invoke(cityName: String): Result<City> {
        val cached = cityRepository.searchCityByName(cityName)
        if (cached != null) return Result.success(cached)

        return aiCityService.searchCity(cityName).onSuccess { city ->
            cityRepository.insertCityFromAi(city)
        }
    }
}
