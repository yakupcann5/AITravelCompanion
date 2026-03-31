package com.travel.companion.feature.translator

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.travel.companion.core.model.TranslationHistoryItem

/**
 * Ceviri ekrani — Kaynak ve hedef dil secimi, metin girisi, AI ceviri sonucu, gecmis listesi.
 *
 * @author Yakup Can
 * @date 2026-03-26
 */
@Composable
internal fun TranslatorScreen(
    state: TranslatorState,
    onIntent: (TranslatorIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        Text(stringResource(R.string.translator_title), style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        LanguageSelector(
            sourceLang = state.sourceLang,
            targetLang = state.targetLang,
            onSourceChanged = { onIntent(TranslatorIntent.SourceLangChanged(it)) },
            onTargetChanged = { onIntent(TranslatorIntent.TargetLangChanged(it)) },
            onSwap = { onIntent(TranslatorIntent.SwapLanguages) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        SourceTextInput(
            sourceText = state.sourceText,
            sourceLang = state.sourceLang,
            onTextChanged = { onIntent(TranslatorIntent.InputTextChanged(it)) },
        )

        Spacer(modifier = Modifier.height(12.dp))

        TranslateActionRow(
            isTranslating = state.isTranslating,
            canTranslate = state.canTranslate,
            onTranslate = { onIntent(TranslatorIntent.Translate) },
            onClear = { onIntent(TranslatorIntent.ClearAll) },
        )

        if (state.error != null) {
            Spacer(modifier = Modifier.height(12.dp))
            ErrorBanner(
                message = state.error,
                onDismiss = { onIntent(TranslatorIntent.DismissError) },
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.hasResult) {
            ResultCard(
                targetText = state.targetText,
                targetLang = state.targetLang,
                onCopy = { onIntent(TranslatorIntent.CopyResult) },
            )
        }

        if (state.history.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            HistorySection(
                history = state.history,
                onItemClick = { item ->
                    onIntent(
                        TranslatorIntent.SelectHistoryItem(
                            sourceText = item.sourceText,
                            targetText = item.targetText,
                            sourceLang = item.sourceLang,
                            targetLang = item.targetLang,
                        ),
                    )
                },
                onClearHistory = { onIntent(TranslatorIntent.ClearHistory) },
            )
        }
    }
}

@Composable
private fun ErrorBanner(
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = message,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
            )
            IconButton(onClick = onDismiss) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = stringResource(R.string.translator_error_dismiss_desc),
                    tint = MaterialTheme.colorScheme.onErrorContainer,
                )
            }
        }
    }
}

@Composable
private fun ResultCard(
    targetText: String,
    targetLang: String,
    onCopy: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = supportedLanguages.firstOrNull { it.first == targetLang }?.second ?: targetLang,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                IconButton(onClick = onCopy) {
                    Icon(
                        Icons.Default.ContentCopy,
                        contentDescription = stringResource(R.string.translator_copy_button_desc),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
            Text(
                text = targetText,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

@Composable
private fun HistorySection(
    history: List<TranslationHistoryItem>,
    onItemClick: (TranslationHistoryItem) -> Unit,
    onClearHistory: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(stringResource(R.string.translator_history_title), style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = onClearHistory) {
                Text(stringResource(R.string.translator_clear_history_button))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        history.forEach { item ->
            HistoryItemCard(
                item = item,
                onClick = { onItemClick(item) },
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun HistoryItemCard(
    item: TranslationHistoryItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sourceLangName = supportedLanguages.firstOrNull { it.first == item.sourceLang }?.second ?: item.sourceLang
    val targetLangName = supportedLanguages.firstOrNull { it.first == item.targetLang }?.second ?: item.targetLang

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = sourceLangName,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "->",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = targetLangName,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.sourceText,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = item.targetText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageSelector(
    sourceLang: String,
    targetLang: String,
    onSourceChanged: (String) -> Unit,
    onTargetChanged: (String) -> Unit,
    onSwap: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        LanguageDropdown(
            selectedLang = sourceLang,
            onLangSelected = onSourceChanged,
            modifier = Modifier.weight(1f),
        )

        IconButton(onClick = onSwap) {
            Icon(Icons.Default.SwapHoriz, contentDescription = stringResource(R.string.translator_swap_languages_desc))
        }

        LanguageDropdown(
            selectedLang = targetLang,
            onLangSelected = onTargetChanged,
            modifier = Modifier.weight(1f),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageDropdown(
    selectedLang: String,
    onLangSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val displayName = supportedLanguages.firstOrNull { it.first == selectedLang }?.second ?: selectedLang

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = displayName,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            supportedLanguages.forEach { (code, name) ->
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = {
                        onLangSelected(code)
                        expanded = false
                    },
                )
            }
        }
    }
}

@Composable
private fun SourceTextInput(
    sourceText: String,
    sourceLang: String,
    onTextChanged: (String) -> Unit,
) {
    OutlinedTextField(
        value = sourceText,
        onValueChange = onTextChanged,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        label = {
            Text(supportedLanguages.firstOrNull { it.first == sourceLang }?.second ?: sourceLang)
        },
        placeholder = { Text(stringResource(R.string.translator_input_placeholder)) },
        shape = RoundedCornerShape(12.dp),
    )
}

@Composable
private fun TranslateActionRow(
    isTranslating: Boolean,
    canTranslate: Boolean,
    onTranslate: () -> Unit,
    onClear: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Button(
            onClick = onTranslate,
            modifier = Modifier.weight(1f),
            enabled = canTranslate,
            shape = RoundedCornerShape(12.dp),
        ) {
            if (isTranslating) {
                CircularProgressIndicator(modifier = Modifier.height(20.dp))
            } else {
                Icon(Icons.Default.Translate, contentDescription = stringResource(R.string.translator_translate_button))
                Spacer(modifier = Modifier.padding(4.dp))
                Text(stringResource(R.string.translator_translate_button))
            }
        }
        IconButton(onClick = onClear) {
            Icon(Icons.Default.DeleteOutline, contentDescription = stringResource(R.string.translator_clear_button_desc))
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
private fun TranslatorScreenPreview() {
    com.travel.companion.core.designsystem.theme.TravelCompanionTheme {
        TranslatorScreen(
            state = TranslatorState(
                sourceText = "Hello, how are you?",
                targetText = "Merhaba, nasilsin?",
                sourceLang = "en",
                targetLang = "tr",
                history = listOf(
                    TranslationHistoryItem(
                        sourceText = "Good morning",
                        targetText = "Gunaydin",
                        sourceLang = "en",
                        targetLang = "tr",
                        timestamp = System.currentTimeMillis(),
                    ),
                ),
            ),
            onIntent = {},
        )
    }
}
