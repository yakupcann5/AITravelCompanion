package com.travel.companion.core.data.repository

/**
 * [OfflineFirstTravelPlanRepository] birim testleri.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
import app.cash.turbine.test
import com.travel.companion.core.database.dao.TravelPlanDao
import com.travel.companion.core.database.entity.TravelPlanEntity
import com.travel.companion.core.model.Budget
import com.travel.companion.core.model.DayPlan
import com.travel.companion.core.model.TravelPlan
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TravelPlanRepositoryTest {

    private lateinit var travelPlanDao: TravelPlanDao
    private lateinit var repository: OfflineFirstTravelPlanRepository

    @Before
    fun setup() {
        travelPlanDao = mockk(relaxed = true)
        repository = OfflineFirstTravelPlanRepository(travelPlanDao, UnconfinedTestDispatcher())
    }

    @Test
    fun `getTravelPlans emits mapped domain models from DAO`() = runTest {
        val entities = listOf(
            createEntity("plan-1", "Istanbul"),
            createEntity("plan-2", "Antalya"),
        )
        every { travelPlanDao.getAllTravelPlans() } returns flowOf(entities)

        repository.getTravelPlans().test {
            val plans = awaitItem()
            assertEquals(2, plans.size)
            assertEquals("Istanbul", plans[0].cityName)
            assertEquals("Antalya", plans[1].cityName)
            awaitComplete()
        }
    }

    @Test
    fun `getTravelPlans emits empty list when DAO has no records`() = runTest {
        every { travelPlanDao.getAllTravelPlans() } returns flowOf(emptyList())

        repository.getTravelPlans().test {
            val plans = awaitItem()
            assertEquals(0, plans.size)
            awaitComplete()
        }
    }

    @Test
    fun `getTravelPlan emits mapped domain model for given id`() = runTest {
        every { travelPlanDao.getTravelPlan("plan-1") } returns flowOf(createEntity("plan-1", "Istanbul"))

        repository.getTravelPlan("plan-1").test {
            val plan = awaitItem()
            assertNotNull(plan)
            assertEquals("plan-1", plan.id)
            assertEquals("Istanbul", plan.cityName)
            awaitComplete()
        }
    }

    @Test
    fun `getTravelPlan maps budget correctly from entity string`() = runTest {
        val entity = createEntity("plan-1", "Istanbul", budget = "high")
        every { travelPlanDao.getTravelPlan("plan-1") } returns flowOf(entity)

        repository.getTravelPlan("plan-1").test {
            val plan = awaitItem()
            assertEquals(Budget.HIGH, plan.budget)
            awaitComplete()
        }
    }

    @Test
    fun `saveTravelPlan calls upsert on DAO with entity`() = runTest {
        val entitySlot = slot<TravelPlanEntity>()
        coEvery { travelPlanDao.upsertTravelPlan(capture(entitySlot)) } returns Unit
        val plan = createDomainPlan("plan-5", "Trabzon")

        repository.saveTravelPlan(plan)

        assertNotNull(entitySlot.captured)
        assertEquals("plan-5", entitySlot.captured.id)
        assertEquals("Trabzon", entitySlot.captured.cityName)
    }

    @Test
    fun `saveTravelPlan serializes budget as lowercase string`() = runTest {
        val entitySlot = slot<TravelPlanEntity>()
        coEvery { travelPlanDao.upsertTravelPlan(capture(entitySlot)) } returns Unit
        val plan = createDomainPlan("plan-6", "Izmir", budget = Budget.LUXURY)

        repository.saveTravelPlan(plan)

        assertEquals("luxury", entitySlot.captured.budget)
    }

    @Test
    fun `deleteTravelPlan delegates id to DAO`() = runTest {
        repository.deleteTravelPlan("plan-1")

        coVerify(exactly = 1) { travelPlanDao.deleteTravelPlan("plan-1") }
    }

    @Test
    fun `deleteTravelPlan does not call upsert`() = runTest {
        repository.deleteTravelPlan("plan-1")

        coVerify(exactly = 0) { travelPlanDao.upsertTravelPlan(any()) }
    }

    private fun createEntity(
        id: String,
        cityName: String,
        budget: String = "medium",
    ) = TravelPlanEntity(
        id = id,
        cityId = cityName.lowercase(),
        cityName = cityName,
        budget = budget,
        interests = "[]",
        daysJson = "[]",
        createdAt = 1711152000000L,
    )

    private fun createDomainPlan(
        id: String,
        cityName: String,
        budget: Budget = Budget.MEDIUM,
    ) = TravelPlan(
        id = id,
        cityId = cityName.lowercase(),
        cityName = cityName,
        days = listOf(DayPlan(dayIndex = 0, title = "Birinci Gun")),
        budget = budget,
        interests = listOf("Tarih"),
        createdAt = 1711152000000L,
    )
}
