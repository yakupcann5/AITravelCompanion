package com.travel.companion.feature.translator

import com.travel.companion.core.common.UiText

/**
 * Ceviri ekrani tek seferlik olaylar.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
sealed interface TranslatorEffect {
    data object CopiedToClipboard : TranslatorEffect
    data class ShowSnackbar(val message: UiText) : TranslatorEffect
}
