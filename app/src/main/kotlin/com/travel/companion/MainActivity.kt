package com.travel.companion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.travel.companion.core.designsystem.theme.TravelCompanionTheme
import com.travel.companion.ui.TravelApp
import dagger.hilt.android.AndroidEntryPoint

/**
 * Uygulamanin tek Activity sinifi. Compose UI'i baslatir.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelCompanionTheme {
                TravelApp()
            }
        }
    }
}
