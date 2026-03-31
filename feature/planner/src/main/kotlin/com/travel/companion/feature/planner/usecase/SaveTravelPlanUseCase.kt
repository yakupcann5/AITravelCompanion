package com.travel.companion.feature.planner.usecase

import com.travel.companion.core.data.repository.TravelPlanRepository
import com.travel.companion.core.model.TravelPlan
import javax.inject.Inject

/**
 * Gezi planini veritabanina kaydeder.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
class SaveTravelPlanUseCase @Inject constructor(
    private val travelPlanRepository: TravelPlanRepository,
) {
    suspend operator fun invoke(plan: TravelPlan) =
        travelPlanRepository.saveTravelPlan(plan)
}
