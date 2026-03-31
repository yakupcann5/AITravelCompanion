package com.travel.companion.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.travel.companion.core.model.AppTheme
import com.travel.companion.core.model.Budget
import com.travel.companion.core.model.UserPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

/**
 * Kullanici tercihlerini DataStore ile yoneten repository.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val themeKey = stringPreferencesKey("theme")
    private val languageKey = stringPreferencesKey("language")
    private val budgetKey = stringPreferencesKey("budget")

    val userPreferences: Flow<UserPreference> = context.dataStore.data.map { prefs ->
        UserPreference(
            theme = prefs[themeKey]?.let { runCatching { AppTheme.valueOf(it) }.getOrNull() } ?: AppTheme.SYSTEM,
            preferredLanguage = prefs[languageKey] ?: "en",
            preferredBudget = prefs[budgetKey]?.let { runCatching { Budget.valueOf(it) }.getOrNull() } ?: Budget.MEDIUM,
        )
    }

    suspend fun updateTheme(theme: AppTheme) {
        context.dataStore.edit { prefs -> prefs[themeKey] = theme.name }
    }

    suspend fun updateLanguage(language: String) {
        context.dataStore.edit { prefs -> prefs[languageKey] = language }
    }

    suspend fun updateBudget(budget: Budget) {
        context.dataStore.edit { prefs -> prefs[budgetKey] = budget.name }
    }
}
