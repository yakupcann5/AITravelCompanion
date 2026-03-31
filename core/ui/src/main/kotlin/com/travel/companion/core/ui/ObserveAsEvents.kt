package com.travel.companion.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

/**
 * Lifecycle-aware effect collection icin yardimci Composable.
 * LaunchedEffect(Unit) yerine kullanilmali — repeatOnLifecycle ile
 * ekran arka planda iken effect kaybetme sorununu onler.
 *
 * @author Yakup Can
 * @date 2026-03-31
 */
@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    onEvent: suspend (T) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifecycleOwner.lifecycle) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(onEvent)
        }
    }
}
