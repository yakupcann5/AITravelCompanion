package com.travel.companion.feature.planner

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.companion.core.ai.voice.VoiceAssistantService
import com.travel.companion.core.ai.voice.VoiceEvent
import com.travel.companion.core.common.UiText
import com.travel.companion.core.data.repository.CityRepository
import com.travel.companion.feature.planner.usecase.GenerateTravelPlanUseCase
import com.travel.companion.feature.planner.usecase.SaveTravelPlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Planlayici ekrani MVI ViewModel.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@HiltViewModel
class PlannerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val generateTravelPlanUseCase: GenerateTravelPlanUseCase,
    private val saveTravelPlanUseCase: SaveTravelPlanUseCase,
    private val cityRepository: CityRepository,
    private val voiceAssistantService: VoiceAssistantService,
) : ViewModel() {

    private val _state = MutableStateFlow(PlannerState())
    val state: StateFlow<PlannerState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PlannerEffect>(replay = 0)
    val effect: SharedFlow<PlannerEffect> = _effect.asSharedFlow()

    private var voiceJob: Job? = null

    init {
        val cityId = savedStateHandle.get<String>("cityId")
        if (cityId != null) {
            loadCity(cityId)
        }
    }

    private fun loadCity(cityId: String) {
        viewModelScope.launch {
            val city = cityRepository.getCity(cityId).firstOrNull()
            if (city != null) {
                reduce { it.copy(cityName = city.name) }
            } else {
                Log.w("PlannerViewModel", "City not found: $cityId")
            }
        }
    }

    fun onIntent(intent: PlannerIntent) {
        when (intent) {
            is PlannerIntent.SetCityName -> reduce { it.copy(cityName = intent.cityName) }
            is PlannerIntent.SetBudget -> reduce { it.copy(budget = intent.budget) }
            is PlannerIntent.ToggleInterest -> toggleInterest(intent.interest)
            is PlannerIntent.SetDuration -> reduce { it.copy(durationDays = intent.days) }
            is PlannerIntent.GeneratePlan -> generatePlan()
            is PlannerIntent.SelectDay -> reduce { it.copy(selectedDayIndex = intent.dayIndex) }
            is PlannerIntent.SavePlan -> savePlan()
            is PlannerIntent.ShowPreferences -> reduce { it.copy(showPreferences = true) }
            is PlannerIntent.HidePreferences -> reduce { it.copy(showPreferences = false) }
            is PlannerIntent.ShowVoiceSheet -> reduce { it.copy(showVoiceSheet = true) }
            is PlannerIntent.HideVoiceSheet -> {
                stopVoice()
                reduce { it.copy(showVoiceSheet = false, isVoiceActive = false) }
            }
            is PlannerIntent.StartVoice -> startVoice()
            is PlannerIntent.StopVoice -> stopVoice()
        }
    }

    private fun toggleInterest(interest: String) {
        reduce { state ->
            val updated = if (interest in state.selectedInterests) {
                state.selectedInterests - interest
            } else {
                state.selectedInterests + interest
            }
            state.copy(selectedInterests = updated)
        }
    }

    private fun generatePlan() {
        val current = _state.value
        if (!current.canGenerate) return

        viewModelScope.launch {
            reduce { it.copy(isGenerating = true, error = null, showPreferences = false) }
            generateTravelPlanUseCase(
                cityName = current.cityName,
                budget = current.budget,
                interests = current.selectedInterests,
                durationDays = current.durationDays,
            ).onSuccess { plan ->
                reduce { it.copy(isGenerating = false, travelPlan = plan, selectedDayIndex = 0) }
            }.onFailure { e ->
                Log.e("PlannerViewModel", "Plan generation failed", e)
                val errorText = UiText.StringResource(R.string.planner_error_generation_failed)
                reduce { it.copy(isGenerating = false, error = errorText) }
                emitEffect(PlannerEffect.ShowSnackbar(errorText))
            }
        }
    }

    private fun savePlan() {
        val plan = _state.value.travelPlan ?: return

        viewModelScope.launch {
            reduce { it.copy(isSaving = true) }
            runCatching { saveTravelPlanUseCase(plan) }
                .onSuccess {
                    reduce { it.copy(isSaving = false) }
                    emitEffect(PlannerEffect.PlanSaved)
                    emitEffect(PlannerEffect.ShowSnackbar(UiText.StringResource(R.string.planner_plan_saved)))
                }
                .onFailure { e ->
                    reduce { it.copy(isSaving = false) }
                    val msg = e.message?.let { UiText.DynamicString(it) }
                        ?: UiText.StringResource(R.string.planner_error_save_failed)
                    emitEffect(PlannerEffect.ShowSnackbar(msg))
                }
        }
    }

    private fun startVoice() {
        reduce { it.copy(isVoiceActive = true, voiceTranscript = "") }
        voiceJob = viewModelScope.launch {
            voiceAssistantService.startSession().collect { event ->
                when (event) {
                    is VoiceEvent.Transcript -> {
                        if (event.isFinal) reduce { it.copy(voiceTranscript = event.text) }
                    }
                    is VoiceEvent.Error -> {
                        reduce { it.copy(isVoiceActive = false) }
                        emitEffect(PlannerEffect.ShowSnackbar(UiText.DynamicString(event.message)))
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun stopVoice() {
        voiceJob?.cancel()
        voiceJob = null
        viewModelScope.launch { voiceAssistantService.endSession() }
        reduce { it.copy(isVoiceActive = false) }
    }

    private fun reduce(transform: (PlannerState) -> PlannerState) {
        _state.update(transform)
    }

    private fun emitEffect(effect: PlannerEffect) {
        viewModelScope.launch { _effect.emit(effect) }
    }
}
