package com.travel.companion.feature.planner.usecase

/**
 * [SaveTravelPlanUseCase] birim testleri.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
import com.travel.companion.core.data.repository.TravelPlanRepository
import com.travel.companion.core.model.Budget
import com.travel.companion.core.model.DayPlan
import com.travel.companion.core.model.TravelPlan
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SaveTravelPlanUseCaseTest {

    private lateinit var travelPlanRepository: TravelPlanRepository
    private lateinit var useCase: SaveTravelPlanUseCase

    @Before
    fun setup() {
        travelPlanRepository = mockk(relaxed = true)
        useCase = SaveTravelPlanUseCase(travelPlanRepository)
    }

    @Test
    fun `delegates to repository when saving plan`() = runTest {
        val plan = createTravelPlan("plan-1", "Istanbul")

        useCase(plan)

        coVerify(exactly = 1) { travelPlanRepository.saveTravelPlan(plan) }
    }

    @Test
    fun `saves plan with correct data`() = runTest {
        val plan = createTravelPlan("plan-2", "Antalya")

        useCase(plan)

        coVerify { travelPlanRepository.saveTravelPlan(plan) }
    }

    @Test
    fun `propagates exception from repository`() = runTest {
        val plan = createTravelPlan("plan-3", "Trabzon")
        val exception = RuntimeException("Database write failed")
        coEvery { travelPlanRepository.saveTravelPlan(plan) } throws exception

        val thrownException = runCatching { useCase(plan) }.exceptionOrNull()

        assert(thrownException == exception)
    }

    @Test
    fun `does not call repository more than once per invocation`() = runTest {
        val plan = createTravelPlan("plan-4", "Cappadocia")

        useCase(plan)

        coVerify(exactly = 1) { travelPlanRepository.saveTravelPlan(any()) }
    }

    private fun createTravelPlan(id: String, cityName: String) = TravelPlan(
        id = id,
        cityId = cityName.lowercase(),
        cityName = cityName,
        days = listOf(
            DayPlan(dayIndex = 0, title = "Birinci Gun"),
        ),
        budget = Budget.MEDIUM,
        interests = listOf("Tarih"),
        createdAt = 1711152000000L,
    )
}
