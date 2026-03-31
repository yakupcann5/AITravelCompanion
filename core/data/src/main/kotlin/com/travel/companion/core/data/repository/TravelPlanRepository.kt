package com.travel.companion.core.data.repository

import com.travel.companion.core.model.TravelPlan
import kotlinx.coroutines.flow.Flow

/**
 * Seyahat plani veri erisim katmani.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
interface TravelPlanRepository {
    fun getTravelPlans(): Flow<List<TravelPlan>>
    fun getTravelPlan(id: String): Flow<TravelPlan>
    suspend fun saveTravelPlan(plan: TravelPlan)
    suspend fun deleteTravelPlan(id: String)
}
