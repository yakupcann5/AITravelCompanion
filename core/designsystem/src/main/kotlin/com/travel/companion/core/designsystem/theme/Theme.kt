package com.travel.companion.core.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = TravelBlue,
    onPrimary = SurfaceLight,
    secondary = TravelOrange,
    onSecondary = SurfaceLight,
    surface = SurfaceLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = TravelBlueLight,
    onPrimary = SurfaceDark,
    secondary = TravelOrangeLight,
    onSecondary = SurfaceDark,
    surface = SurfaceDark,
)

/**
 * Uygulama Material 3 tema tanimlamasi.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Composable
fun TravelCompanionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = TravelTypography,
        content = content,
    )
}
