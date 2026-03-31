package com.travel.companion.feature.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.window.core.layout.WindowWidthSizeClass
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.travel.companion.core.model.GalleryImage
import com.travel.companion.core.ui.ErrorScreen
import com.travel.companion.core.ui.LoadingIndicator

/**
 * Galeri ekrani — AI gorsel uretimi ve Pinterest tarzi grid.
 *
 * @author Yakup Can
 * @date 2026-03-17
 */
@Composable
internal fun GalleryScreen(
    state: GalleryState,
    onIntent: (GalleryIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        state.isLoading -> LoadingIndicator(modifier)
        state.error != null -> ErrorScreen(
            message = state.error,
            onRetry = { onIntent(GalleryIntent.LoadImages) },
            modifier = modifier,
        )
        else -> GalleryContent(state = state, onIntent = onIntent, modifier = modifier)
    }

    state.selectedImage?.let { image ->
        ImageDetailSheet(
            image = image,
            onDismiss = { onIntent(GalleryIntent.ClearSelection) },
            onShare = { onIntent(GalleryIntent.ShareImage(image.id)) },
            onFavorite = { onIntent(GalleryIntent.ToggleFavorite(image.id)) },
        )
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun GalleryContent(
    state: GalleryState,
    onIntent: (GalleryIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowWidth = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
    val isExpanded = windowWidth == WindowWidthSizeClass.EXPANDED

    if (isExpanded) {
        ExpandedGalleryLayout(state = state, onIntent = onIntent, modifier = modifier)
    } else {
        CompactGalleryLayout(state = state, onIntent = onIntent, modifier = modifier)
    }
}

@Composable
private fun CompactGalleryLayout(
    state: GalleryState,
    onIntent: (GalleryIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        GalleryHeader(state = state, onIntent = onIntent)
        GalleryGrid(
            images = state.images,
            onImageClick = { onIntent(GalleryIntent.SelectImage(it)) },
            onFavoriteClick = { onIntent(GalleryIntent.ToggleFavorite(it)) },
        )
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun ExpandedGalleryLayout(
    state: GalleryState,
    onIntent: (GalleryIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<String>()
    val scope = rememberCoroutineScope()

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                Column(modifier = Modifier.fillMaxSize()) {
                    GalleryHeader(state = state, onIntent = onIntent)
                    GalleryGrid(
                        images = state.images,
                        onImageClick = { imageId ->
                            onIntent(GalleryIntent.SelectImage(imageId))
                            scope.launch {
                                navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, imageId)
                            }
                        },
                        onFavoriteClick = { onIntent(GalleryIntent.ToggleFavorite(it)) },
                        columns = 4,
                    )
                }
            }
        },
        detailPane = {
            AnimatedPane {
                val selectedImage = state.selectedImage
                if (selectedImage != null) {
                    GalleryDetailPane(
                        image = selectedImage,
                        onShare = { onIntent(GalleryIntent.ShareImage(selectedImage.id)) },
                        onFavorite = { onIntent(GalleryIntent.ToggleFavorite(selectedImage.id)) },
                    )
                }
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun GalleryHeader(
    state: GalleryState,
    onIntent: (GalleryIntent) -> Unit,
) {
    Text(
        text = stringResource(R.string.gallery_title),
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
    )
    PromptInputSection(
        prompt = state.prompt,
        isGenerating = state.isGenerating,
        canGenerate = state.canGenerate,
        onPromptChanged = { onIntent(GalleryIntent.PromptChanged(it)) },
        onGenerate = { onIntent(GalleryIntent.GenerateImage) },
    )
    if (state.isGenerating) {
        Text(
            text = stringResource(R.string.gallery_generating_message),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            color = MaterialTheme.colorScheme.primary,
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
private fun GalleryDetailPane(
    image: GalleryImage,
    onShare: () -> Unit,
    onFavorite: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
    ) {
        AsyncImage(
            model = image.imageUrl,
            contentDescription = image.prompt,
            modifier = Modifier.fillMaxWidth().height(300.dp).clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = image.prompt, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Button(onClick = onShare, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.Share, contentDescription = stringResource(R.string.gallery_share_button_desc))
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.gallery_share_button))
            }
            Button(onClick = onFavorite, modifier = Modifier.weight(1f)) {
                Icon(
                    if (image.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.gallery_favorite_button_desc),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    if (image.isFavorite) stringResource(R.string.gallery_remove_favorite_button)
                    else stringResource(R.string.gallery_add_favorite_button),
                )
            }
        }
    }
}

@Composable
private fun PromptInputSection(
    prompt: String,
    isGenerating: Boolean,
    canGenerate: Boolean,
    onPromptChanged: (String) -> Unit,
    onGenerate: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = prompt,
            onValueChange = onPromptChanged,
            modifier = Modifier.weight(1f),
            placeholder = { Text(stringResource(R.string.gallery_prompt_placeholder)) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
        )
        Button(onClick = onGenerate, enabled = canGenerate, shape = RoundedCornerShape(12.dp)) {
            if (isGenerating) {
                CircularProgressIndicator(modifier = Modifier.height(20.dp))
            } else {
                Icon(Icons.Default.AutoAwesome, contentDescription = stringResource(R.string.gallery_generate_button_desc))
            }
        }
    }
}

@Composable
private fun GalleryGrid(
    images: List<GalleryImage>,
    onImageClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    columns: Int? = null,
) {
    val resolvedColumns = columns ?: run {
        val windowWidth = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
        when (windowWidth) {
            WindowWidthSizeClass.COMPACT -> 2
            WindowWidthSizeClass.MEDIUM -> 3
            else -> 4
        }
    }
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(resolvedColumns),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalItemSpacing = 10.dp,
    ) {
        items(images, key = { it.id }) { image ->
            GalleryImageCard(
                image = image,
                onClick = { onImageClick(image.id) },
                onFavoriteClick = { onFavoriteClick(image.id) },
            )
        }
    }
}

@Composable
private fun GalleryImageCard(
    image: GalleryImage,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Box {
            AsyncImage(
                model = image.imageUrl,
                contentDescription = image.prompt,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop,
            )

            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.align(Alignment.TopEnd),
            ) {
                Icon(
                    imageVector = if (image.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.gallery_favorite_button_desc),
                    tint = if (image.isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Text(
            text = image.prompt,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(8.dp),
        )
    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
private fun ImageDetailSheet(
    image: GalleryImage,
    onDismiss: () -> Unit,
    onShare: () -> Unit,
    onFavorite: () -> Unit,
) {
    androidx.compose.material3.ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            AsyncImage(
                model = image.imageUrl,
                contentDescription = image.prompt,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = image.prompt,
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Button(
                    onClick = onShare,
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(
                        androidx.compose.material.icons.Icons.Default.Share,
                        contentDescription = stringResource(R.string.gallery_share_button_desc),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.gallery_share_button))
                }

                Button(
                    onClick = onFavorite,
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(
                        if (image.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(R.string.gallery_favorite_button_desc),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (image.isFavorite) stringResource(R.string.gallery_remove_favorite_button) else stringResource(R.string.gallery_add_favorite_button))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
private fun GalleryScreenPreview() {
    com.travel.companion.core.designsystem.theme.TravelCompanionTheme {
        GalleryScreen(
            state = GalleryState(
                images = sampleGalleryImages,
                prompt = "Istanbul Bogazici",
            ),
            onIntent = {},
        )
    }
}
