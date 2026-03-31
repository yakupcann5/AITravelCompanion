package com.travel.companion.feature.planner

/**
 * [PlannerViewModel] birim testleri.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.travel.companion.core.ai.AiPlannerService
import com.travel.companion.core.ai.voice.VoiceAssistantService
import com.travel.companion.core.ai.voice.VoiceEvent
import com.travel.companion.core.data.repository.CityRepository
import com.travel.companion.core.data.repository.TravelPlanRepository
import com.travel.companion.core.model.Budget
import com.travel.companion.core.model.TravelPlan
import com.travel.companion.core.testing.MainDispatcherRule
import com.travel.companion.core.testing.testCity
import com.travel.companion.core.testing.testTravelPlan
import com.travel.companion.feature.planner.usecase.GenerateTravelPlanUseCase
import com.travel.companion.feature.planner.usecase.SaveTravelPlanUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlannerViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var fakePlanResult: Result<TravelPlan> = Result.success(testTravelPlan)
    private lateinit var travelPlanRepository: TravelPlanRepository
    private lateinit var cityRepository: CityRepository
    private lateinit var fakeVoiceAssistantService: FakeVoiceAssistantService

    private val fakeAiPlannerService = object : AiPlannerService {
        override suspend fun generateTravelPlan(
            cityName: String, budget: Budget, interests: List<String>, durationDays: Int,
        ): Result<TravelPlan> = fakePlanResult

        override suspend fun suggestRestaurants(
            cityName: String, cuisine: String, budget: Budget,
        ): Result<List<String>> = Result.success(emptyList())
    }

    @Before
    fun setup() {
        travelPlanRepository = mockk(relaxed = true)
        cityRepository = mockk(relaxed = true)
        fakeVoiceAssistantService = FakeVoiceAssistantService()
    }

    private fun createViewModel(cityId: String? = null): PlannerViewModel {
        val handle = SavedStateHandle(if (cityId != null) mapOf("cityId" to cityId) else emptyMap())
        return PlannerViewModel(
            handle,
            GenerateTravelPlanUseCase(fakeAiPlannerService),
            SaveTravelPlanUseCase(travelPlanRepository),
            cityRepository,
            fakeVoiceAssistantService,
        )
    }

    @Test fun `SetCityName updates state`() = runTest {
        val vm = createViewModel()
        vm.onIntent(PlannerIntent.SetCityName("Ankara"))
        assertEquals("Ankara", vm.state.value.cityName)
    }

    @Test fun `SetBudget updates state`() = runTest {
        val vm = createViewModel()
        vm.onIntent(PlannerIntent.SetBudget(Budget.LUXURY))
        assertEquals(Budget.LUXURY, vm.state.value.budget)
    }

    @Test fun `ToggleInterest adds and removes`() = runTest {
        val vm = createViewModel()
        vm.onIntent(PlannerIntent.ToggleInterest("Tarih"))
        assertTrue(vm.state.value.selectedInterests.contains("Tarih"))
        vm.onIntent(PlannerIntent.ToggleInterest("Tarih"))
        assertFalse(vm.state.value.selectedInterests.contains("Tarih"))
    }

    @Test fun `SetDuration updates state`() = runTest {
        val vm = createViewModel()
        vm.onIntent(PlannerIntent.SetDuration(7))
        assertEquals(7, vm.state.value.durationDays)
    }

    @Test fun `GeneratePlan with success updates travelPlan`() = runTest {
        fakePlanResult = Result.success(testTravelPlan)
        val vm = createViewModel()
        vm.onIntent(PlannerIntent.SetCityName("Istanbul"))
        vm.onIntent(PlannerIntent.GeneratePlan)
        advanceUntilIdle()
        assertNotNull(vm.state.value.travelPlan)
        assertFalse(vm.state.value.isGenerating)
    }

    @Test fun `GeneratePlan with failure sets error`() = runTest {
        fakePlanResult = Result.failure(RuntimeException("AI error"))
        val vm = createViewModel()
        vm.onIntent(PlannerIntent.SetCityName("Istanbul"))
        vm.onIntent(PlannerIntent.GeneratePlan)
        advanceUntilIdle()
        assertNotNull(vm.state.value.error)
        assertFalse(vm.state.value.isGenerating)
    }

    @Test fun `GeneratePlan with blank city does nothing`() = runTest {
        val vm = createViewModel()
        vm.onIntent(PlannerIntent.GeneratePlan)
        advanceUntilIdle()
        assertNull(vm.state.value.travelPlan)
    }

    @Test fun `SelectDay updates index`() = runTest {
        val vm = createViewModel()
        vm.onIntent(PlannerIntent.SelectDay(2))
        assertEquals(2, vm.state.value.selectedDayIndex)
    }

    @Test fun `SavePlan emits PlanSaved`() = runTest {
        fakePlanResult = Result.success(testTravelPlan)
        val vm = createViewModel()
        vm.onIntent(PlannerIntent.SetCityName("Istanbul"))
        vm.onIntent(PlannerIntent.GeneratePlan)
        advanceUntilIdle()

        vm.effect.test {
            vm.onIntent(PlannerIntent.SavePlan)
            advanceUntilIdle()
            assertTrue(awaitItem() is PlannerEffect.PlanSaved)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test fun `ShowPreferences and HidePreferences toggle`() = runTest {
        val vm = createViewModel()
        vm.onIntent(PlannerIntent.HidePreferences)
        assertFalse(vm.state.value.showPreferences)
        vm.onIntent(PlannerIntent.ShowPreferences)
        assertTrue(vm.state.value.showPreferences)
    }

    @Test fun `init with cityId loads city name`() = runTest {
        coEvery { cityRepository.getCity("c1") } returns flowOf(testCity)
        val vm = createViewModel(cityId = "c1")
        advanceUntilIdle()
        assertEquals("Istanbul", vm.state.value.cityName)
    }

    @Test fun `ShowVoiceSheet and HideVoiceSheet toggle showVoiceSheet`() = runTest {
        val vm = createViewModel()
        vm.onIntent(PlannerIntent.ShowVoiceSheet)
        assertTrue(vm.state.value.showVoiceSheet)
        vm.onIntent(PlannerIntent.HideVoiceSheet)
        assertFalse(vm.state.value.showVoiceSheet)
    }

    @Test fun `StartVoice sets isVoiceActive to true`() = runTest {
        val vm = createViewModel()
        vm.onIntent(PlannerIntent.StartVoice)
        advanceUntilIdle()
        assertTrue(vm.state.value.isVoiceActive)
    }

    @Test fun `StopVoice sets isVoiceActive to false`() = runTest {
        val vm = createViewModel()
        vm.onIntent(PlannerIntent.StartVoice)
        advanceUntilIdle()
        vm.onIntent(PlannerIntent.StopVoice)
        advanceUntilIdle()
        assertFalse(vm.state.value.isVoiceActive)
    }

    @Test fun `StartVoice updates voiceTranscript from Transcript event`() = runTest {
        val vm = createViewModel()
        vm.onIntent(PlannerIntent.StartVoice)
        advanceUntilIdle()

        fakeVoiceAssistantService.events.emit(VoiceEvent.Transcript("merhaba", isFinal = true))
        advanceUntilIdle()

        assertEquals("merhaba", vm.state.value.voiceTranscript)
    }

    @Test fun `Voice Error event emits ShowSnackbar effect`() = runTest {
        val vm = createViewModel()
        vm.onIntent(PlannerIntent.StartVoice)
        advanceUntilIdle()

        vm.effect.test {
            fakeVoiceAssistantService.events.emit(VoiceEvent.Error("mic failure"))
            advanceUntilIdle()
            assertTrue(awaitItem() is PlannerEffect.ShowSnackbar)
            cancelAndIgnoreRemainingEvents()
        }
    }
}

private class FakeVoiceAssistantService : VoiceAssistantService {
    val events = MutableSharedFlow<VoiceEvent>(replay = 0, extraBufferCapacity = 8)
    var endSessionCalled = false

    override fun startSession(): Flow<VoiceEvent> = events
    override suspend fun sendAudio(audioData: ByteArray) = Unit
    override suspend fun sendText(text: String) = Unit
    override suspend fun endSession() { endSessionCalled = true }
    override fun isAvailable(): Boolean = true
}
