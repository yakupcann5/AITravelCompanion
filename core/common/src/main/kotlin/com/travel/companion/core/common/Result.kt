package com.travel.companion.core.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Uygulama genelinde kullanilan sonuc sarmalayici sealed interface.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
sealed interface AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>
    data class Error(val exception: Throwable) : AppResult<Nothing>
    data object Loading : AppResult<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<AppResult<T>> =
    map<T, AppResult<T>> { AppResult.Success(it) }
        .catch { emit(AppResult.Error(it)) }
