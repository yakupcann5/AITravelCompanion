package com.travel.companion.core.common

import android.content.Context
import androidx.annotation.StringRes

/**
 * ViewModel'den UI katmanina string iletmek icin kullanilan sealed interface.
 * ViewModel'de Context bagimliligini ortadan kaldirir ve i18n destegi saglar.
 *
 * @author Yakup Can
 * @date 2026-03-29
 */
sealed interface UiText {

    data class StringResource(
        @StringRes val resId: Int,
        val args: List<Any> = emptyList(),
    ) : UiText

    data class DynamicString(val value: String) : UiText

    fun asString(context: Context): String = when (this) {
        is StringResource -> context.getString(resId, *args.toTypedArray())
        is DynamicString -> value
    }
}
