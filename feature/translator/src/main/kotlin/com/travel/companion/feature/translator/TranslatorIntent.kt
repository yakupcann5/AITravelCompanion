package com.travel.companion.feature.translator

/**
 * Ceviri ekrani kullanici aksiyonlari.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
sealed interface TranslatorIntent {
    data class InputTextChanged(val text: String) : TranslatorIntent
    data class SourceLangChanged(val lang: String) : TranslatorIntent
    data class TargetLangChanged(val lang: String) : TranslatorIntent
    data object Translate : TranslatorIntent
    data object SwapLanguages : TranslatorIntent
    data object CopyResult : TranslatorIntent
    data object ClearAll : TranslatorIntent
    data object LoadHistory : TranslatorIntent
    data object ClearHistory : TranslatorIntent
    data object DismissError : TranslatorIntent
    data class SelectHistoryItem(
        val sourceText: String,
        val targetText: String,
        val sourceLang: String,
        val targetLang: String,
    ) : TranslatorIntent
}
