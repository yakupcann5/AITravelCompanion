package com.travel.companion.feature.explore

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.travel.companion.core.ui.ObserveAsEvents

/**
 * Kesfet ekrani navigation route.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Composable
fun ExploreRoute(
    onNavigateToPlanner: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExploreViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    ObserveAsEvents(viewModel.effect) { effect ->
        when (effect) {
            is ExploreEffect.NavigateToPlanner -> onNavigateToPlanner(effect.cityId)
            is ExploreEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message.asString(context))
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier,
    ) { innerPadding ->
        ExploreScreen(
            state = state,
            onIntent = viewModel::onIntent,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
