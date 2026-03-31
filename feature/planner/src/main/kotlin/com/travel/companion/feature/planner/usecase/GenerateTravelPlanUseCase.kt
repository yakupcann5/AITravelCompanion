package com.travel.companion.feature.planner.usecase

import com.travel.companion.core.ai.AiPlannerService
import com.travel.companion.core.model.Budget
import com.travel.companion.core.model.TravelPlan
import javax.inject.Inject

/**
 * AI ile gezi plani uretimi use case'i.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
class GenerateTravelPlanUseCase @Inject constructor(
    private val aiPlannerService: AiPlannerService,
) {
    suspend operator fun invoke(
        cityName: String,
        budget: Budget,
        interests: List<String>,
        durationDays: Int,
    ): Result<TravelPlan> = aiPlannerService.generateTravelPlan(
        cityName = cityName,
        budget = budget,
        interests = interests,
        durationDays = durationDays,
    )
}
