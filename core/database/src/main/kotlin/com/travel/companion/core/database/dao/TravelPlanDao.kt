package com.travel.companion.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.travel.companion.core.database.entity.TravelPlanEntity
import kotlinx.coroutines.flow.Flow

/**
 * Seyahat plani Room DAO interface'i.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Dao
interface TravelPlanDao {

    @Query("SELECT * FROM travel_plans ORDER BY createdAt DESC")
    fun getAllTravelPlans(): Flow<List<TravelPlanEntity>>

    @Query("SELECT * FROM travel_plans WHERE id = :id")
    fun getTravelPlan(id: String): Flow<TravelPlanEntity>

    @Upsert
    suspend fun upsertTravelPlan(plan: TravelPlanEntity)

    @Query("DELETE FROM travel_plans WHERE id = :id")
    suspend fun deleteTravelPlan(id: String)
}
