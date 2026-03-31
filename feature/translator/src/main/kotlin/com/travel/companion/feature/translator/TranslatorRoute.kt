package com.travel.companion.feature.translator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.travel.companion.core.ui.ObserveAsEvents

/**
 * Ceviri ekrani navigation route.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Composable
fun TranslatorRoute(
    modifier: Modifier = Modifier,
    viewModel: TranslatorViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val copiedMessage = stringResource(R.string.translator_copied_snackbar)

    ObserveAsEvents(viewModel.effect) { effect ->
        when (effect) {
            is TranslatorEffect.CopiedToClipboard -> {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.setPrimaryClip(ClipData.newPlainText("translation", viewModel.state.value.targetText))
                snackbarHostState.showSnackbar(copiedMessage)
            }
            is TranslatorEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message.asString(context))
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier,
    ) { innerPadding ->
        TranslatorScreen(
            state = state,
            onIntent = viewModel::onIntent,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
