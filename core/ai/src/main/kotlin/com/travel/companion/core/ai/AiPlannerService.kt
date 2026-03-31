package com.travel.companion.core.ai

import com.travel.companion.core.model.Budget
import com.travel.companion.core.model.TravelPlan

/**
 * AI gezi planlama servisi abstraction layer.
 * Gemini Flash ile cloud-based gezi plani uretimi yapar.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
interface AiPlannerService {
    suspend fun generateTravelPlan(
        cityName: String,
        budget: Budget,
        interests: List<String>,
        durationDays: Int,
    ): Result<TravelPlan>

    suspend fun suggestRestaurants(
        cityName: String,
        cuisine: String,
        budget: Budget,
    ): Result<List<String>>
}
