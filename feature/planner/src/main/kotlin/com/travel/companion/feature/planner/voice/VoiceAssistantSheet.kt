package com.travel.companion.feature.planner.voice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.travel.companion.feature.planner.R

/**
 * Sesli asistan alt sayfa Compose UI.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceAssistantSheet(
    isActive: Boolean,
    transcript: String,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(R.string.planner_voice_title),
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                text = if (transcript.isNotBlank()) transcript else stringResource(R.string.planner_voice_prompt),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            Spacer(modifier = Modifier.height(8.dp))

            FilledIconButton(
                onClick = { if (isActive) onStop() else onStart() },
                modifier = Modifier.size(72.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = if (isActive) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                ),
            ) {
                Icon(
                    imageVector = if (isActive) Icons.Default.MicOff else Icons.Default.Mic,
                    contentDescription = if (isActive) stringResource(R.string.planner_voice_stop_desc) else stringResource(R.string.planner_voice_start_desc),
                    modifier = Modifier.size(36.dp),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
