package com.travel.companion.feature.translator

import com.travel.companion.core.model.TranslationHistoryItem

/**
 * Ceviri ekrani UI durumu.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
data class TranslatorState(
    val sourceText: String = "",
    val targetText: String = "",
    val sourceLang: String = "en",
    val targetLang: String = "tr",
    val isTranslating: Boolean = false,
    val error: String? = null,
    val history: List<TranslationHistoryItem> = emptyList(),
) {
    val canTranslate: Boolean get() = sourceText.isNotBlank() && !isTranslating
    val hasResult: Boolean get() = targetText.isNotBlank()
}

val supportedLanguages = listOf(
    "tr" to "Turkce",
    "en" to "Ingilizce",
    "de" to "Almanca",
    "fr" to "Fransizca",
    "es" to "Ispanyolca",
    "it" to "Italyanca",
    "ja" to "Japonca",
    "ar" to "Arapca",
    "ru" to "Rusca",
    "zh" to "Cince",
)
