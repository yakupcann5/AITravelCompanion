package com.travel.companion.core.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

/**
 * Kullanici tercihleri domain modeli.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Immutable
@Serializable
data class UserPreference(
    val theme: AppTheme = AppTheme.SYSTEM,
    val preferredLanguage: String = "en",
    val preferredBudget: Budget = Budget.MEDIUM,
    val preferredInterests: List<String> = emptyList(),
)

@Serializable
enum class AppTheme {
    LIGHT, DARK, SYSTEM
}
