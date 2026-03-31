package com.travel.companion.feature.gallery

import android.content.Intent
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
 * Galeri ekrani navigation route.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Composable
fun GalleryRoute(
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val savedMessage = stringResource(R.string.gallery_saved_snackbar)
    val shareTitle = stringResource(R.string.gallery_share_chooser_title)

    ObserveAsEvents(viewModel.effect) { effect ->
        when (effect) {
            is GalleryEffect.ImageSaved -> snackbarHostState.showSnackbar(savedMessage)
            is GalleryEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message.asString(context))
            is GalleryEffect.ShareImage -> {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "${effect.prompt}\n${effect.imageUrl}")
                }
                context.startActivity(Intent.createChooser(shareIntent, shareTitle))
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier,
    ) { innerPadding ->
        GalleryScreen(
            state = state,
            onIntent = viewModel::onIntent,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
