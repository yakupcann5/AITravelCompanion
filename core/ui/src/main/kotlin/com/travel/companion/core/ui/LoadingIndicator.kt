package com.travel.companion.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Yukleme durumunu gosteren yeniden kullanilabilir Compose bileseni.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
private fun LoadingIndicatorPreview() {
    com.travel.companion.core.designsystem.theme.TravelCompanionTheme {
        LoadingIndicator()
    }
}
