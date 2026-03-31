package com.travel.companion.feature.translator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.companion.core.common.UiText
import com.travel.companion.core.data.repository.TranslationHistoryRepository
import com.travel.companion.core.model.TranslationHistoryItem
import com.travel.companion.feature.translator.usecase.TranslateTextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Ceviri ekrani MVI ViewModel.
 * On-device Gemini Nano ile offline ceviri, cloud fallback destegi.
 * Gecmis ceviri kayitlari Room veritabaninda tutulur.
 *
 * @author Yakup Can
 * @date 2026-03-26
 */
@HiltViewModel
class TranslatorViewModel @Inject constructor(
    private val translateTextUseCase: TranslateTextUseCase,
    private val translationHistoryRepository: TranslationHistoryRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(TranslatorState())
    val state: StateFlow<TranslatorState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<TranslatorEffect>(replay = 0)
    val effect: SharedFlow<TranslatorEffect> = _effect.asSharedFlow()

    init {
        onIntent(TranslatorIntent.LoadHistory)
    }

    fun onIntent(intent: TranslatorIntent) {
        when (intent) {
            is TranslatorIntent.InputTextChanged -> reduce { it.copy(sourceText = intent.text) }
            is TranslatorIntent.SourceLangChanged -> reduce { it.copy(sourceLang = intent.lang) }
            is TranslatorIntent.TargetLangChanged -> reduce { it.copy(targetLang = intent.lang) }
            is TranslatorIntent.Translate -> translate()
            is TranslatorIntent.SwapLanguages -> reduce {
                it.copy(
                    sourceLang = it.targetLang,
                    targetLang = it.sourceLang,
                    sourceText = it.targetText,
                    targetText = it.sourceText,
                )
            }
            is TranslatorIntent.CopyResult -> emitEffect(TranslatorEffect.CopiedToClipboard)
            is TranslatorIntent.ClearAll -> reduce { TranslatorState(sourceLang = it.sourceLang, targetLang = it.targetLang, history = it.history) }
            is TranslatorIntent.LoadHistory -> observeHistory()
            is TranslatorIntent.ClearHistory -> clearHistory()
            is TranslatorIntent.DismissError -> reduce { it.copy(error = null) }
            is TranslatorIntent.SelectHistoryItem -> reduce {
                it.copy(
                    sourceText = intent.sourceText,
                    targetText = intent.targetText,
                    sourceLang = intent.sourceLang,
                    targetLang = intent.targetLang,
                )
            }
        }
    }

    private fun translate() {
        val current = _state.value
        if (!current.canTranslate) return

        viewModelScope.launch {
            reduce { it.copy(isTranslating = true, error = null) }
            translateTextUseCase(current.sourceText, current.targetLang)
                .onSuccess { result ->
                    reduce { it.copy(isTranslating = false, targetText = result) }
                    saveToHistory(current.sourceText, result, current.sourceLang, current.targetLang)
                }
                .onFailure { e ->
                    reduce { it.copy(isTranslating = false, error = e.message) }
                    val msg = e.message?.let { UiText.DynamicString(it) }
                        ?: UiText.StringResource(R.string.translator_error_failed)
                    emitEffect(TranslatorEffect.ShowSnackbar(msg))
                }
        }
    }

    private fun observeHistory() {
        viewModelScope.launch {
            translationHistoryRepository.getRecentTranslations().collect { items ->
                reduce { it.copy(history = items) }
            }
        }
    }

    private fun saveToHistory(
        sourceText: String,
        targetText: String,
        sourceLang: String,
        targetLang: String,
    ) {
        viewModelScope.launch {
            translationHistoryRepository.insertTranslation(
                TranslationHistoryItem(
                    sourceText = sourceText,
                    targetText = targetText,
                    sourceLang = sourceLang,
                    targetLang = targetLang,
                    timestamp = System.currentTimeMillis(),
                ),
            )
        }
    }

    private fun clearHistory() {
        viewModelScope.launch {
            translationHistoryRepository.clearHistory()
        }
    }

    private fun reduce(transform: (TranslatorState) -> TranslatorState) {
        _state.update(transform)
    }

    private fun emitEffect(effect: TranslatorEffect) {
        viewModelScope.launch { _effect.emit(effect) }
    }
}
