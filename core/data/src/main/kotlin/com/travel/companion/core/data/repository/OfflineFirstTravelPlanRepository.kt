package com.travel.companion.core.data.repository

import com.travel.companion.core.common.di.IoDispatcher
import com.travel.companion.core.data.mapper.toDomain
import com.travel.companion.core.data.mapper.toEntity
import com.travel.companion.core.database.dao.TravelPlanDao
import com.travel.companion.core.model.TravelPlan
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Offline-first seyahat plani repository'si.
 * Planlari Room veritabaninda saklar.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Singleton
class OfflineFirstTravelPlanRepository @Inject constructor(
    private val travelPlanDao: TravelPlanDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : TravelPlanRepository {

    override fun getTravelPlans(): Flow<List<TravelPlan>> =
        travelPlanDao.getAllTravelPlans().map { entities -> entities.map { it.toDomain() } }

    override fun getTravelPlan(id: String): Flow<TravelPlan> =
        travelPlanDao.getTravelPlan(id).map { it.toDomain() }

    override suspend fun saveTravelPlan(plan: TravelPlan) = withContext(ioDispatcher) {
        travelPlanDao.upsertTravelPlan(plan.toEntity())
    }

    override suspend fun deleteTravelPlan(id: String) = withContext(ioDispatcher) {
        travelPlanDao.deleteTravelPlan(id)
    }
}
