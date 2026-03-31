package com.travel.companion.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.travel.companion.core.common.UiText

/**
 * [UiText] icin Compose extension fonksiyonu.
 * Screen composable'larinda state.error gibi alanlari resolve etmek icin kullanilir.
 *
 * @author Yakup Can
 * @date 2026-03-29
 */
@Composable
fun UiText.asString(): String = when (this) {
    is UiText.StringResource -> stringResource(resId, *args.toTypedArray())
    is UiText.DynamicString -> value
}
