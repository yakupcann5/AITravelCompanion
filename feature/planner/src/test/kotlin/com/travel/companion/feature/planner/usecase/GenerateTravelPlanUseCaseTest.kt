package com.travel.companion.feature.planner.usecase

/**
 * [GenerateTravelPlanUseCase] birim testleri.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
import com.travel.companion.core.ai.AiPlannerService
import com.travel.companion.core.model.Budget
import com.travel.companion.core.model.TravelPlan
import com.travel.companion.core.testing.testTravelPlan
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GenerateTravelPlanUseCaseTest {

    @Test fun `returns plan when service succeeds`() = runTest {
        val useCase = GenerateTravelPlanUseCase(FakeAiPlannerService(Result.success(testTravelPlan)))
        val result = useCase("Istanbul", Budget.MEDIUM, listOf("Tarih"), 3)
        assertTrue(result.isSuccess)
        assertEquals(testTravelPlan, result.getOrNull())
    }

    @Test fun `returns failure when service fails`() = runTest {
        val useCase = GenerateTravelPlanUseCase(FakeAiPlannerService(Result.failure(RuntimeException("err"))))
        assertTrue(useCase("Istanbul", Budget.MEDIUM, emptyList(), 3).isFailure)
    }

    @Test fun `propagates network error`() = runTest {
        val useCase = GenerateTravelPlanUseCase(FakeAiPlannerService(Result.failure(RuntimeException("network"))))
        assertTrue(useCase("Paris", Budget.LOW, listOf("Kultur"), 5).isFailure)
    }
}

private class FakeAiPlannerService(private val result: Result<TravelPlan>) : AiPlannerService {
    override suspend fun generateTravelPlan(
        cityName: String, budget: Budget, interests: List<String>, durationDays: Int,
    ) = result

    override suspend fun suggestRestaurants(cityName: String, cuisine: String, budget: Budget) =
        Result.success(emptyList<String>())
}
