package com.travel.companion.feature.explore.usecase

import com.travel.companion.core.data.repository.CityRepository
import com.travel.companion.core.model.City
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Son aramalari getirme is mantigi use case'i.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
class GetRecentSearchesUseCase @Inject constructor(
    private val cityRepository: CityRepository,
) {
    operator fun invoke(): Flow<List<City>> = cityRepository.getRecentSearches()
}
