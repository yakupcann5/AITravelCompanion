package com.travel.companion.feature.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.companion.core.common.UiText
import com.travel.companion.core.data.repository.GeneratedImageRepository
import com.travel.companion.core.model.GalleryImage
import com.travel.companion.feature.gallery.usecase.GenerateImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

/**
 * Galeri ekrani MVI ViewModel.
 * AI gorsel uretimi ve galeri yonetimi.
 *
 * @author Yakup Can
 * @date 2026-03-17
 */
@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val generateImageUseCase: GenerateImageUseCase,
    private val generatedImageRepository: GeneratedImageRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(GalleryState())
    val state: StateFlow<GalleryState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<GalleryEffect>(replay = 0)
    val effect: SharedFlow<GalleryEffect> = _effect.asSharedFlow()

    private val prepopulateMutex = Mutex()
    private var hasPrepopulated = false

    init {
        onIntent(GalleryIntent.LoadImages)
    }

    fun onIntent(intent: GalleryIntent) {
        when (intent) {
            is GalleryIntent.LoadImages -> loadImages()
            is GalleryIntent.PromptChanged -> reduce { it.copy(prompt = intent.prompt) }
            is GalleryIntent.GenerateImage -> generateImage()
            is GalleryIntent.ToggleFavorite -> toggleFavorite(intent.imageId)
            is GalleryIntent.SelectImage -> reduce { it.copy(selectedImageId = intent.imageId) }
            is GalleryIntent.ClearSelection -> reduce { it.copy(selectedImageId = null) }
            is GalleryIntent.ShareImage -> shareImage(intent.imageId)
        }
    }

    private fun loadImages() {
        reduce { it.copy(isLoading = true) }
        viewModelScope.launch {
            generatedImageRepository.getAllImages().collect { images ->
                if (images.isEmpty() && !hasPrepopulated) {
                    prepopulateMutex.withLock {
                        if (!hasPrepopulated) {
                            hasPrepopulated = true
                            prepopulateWithSamples()
                        }
                    }
                } else if (images.isNotEmpty()) {
                    reduce { it.copy(isLoading = false, images = images) }
                }
            }
        }
    }

    private suspend fun prepopulateWithSamples() {
        sampleGalleryImages.forEach { image ->
            generatedImageRepository.upsertImage(image)
        }
    }

    private fun generateImage() {
        val prompt = _state.value.prompt
        if (!_state.value.canGenerate) return

        viewModelScope.launch {
            reduce { it.copy(isGenerating = true) }
            generateImageUseCase(prompt)
                .onSuccess { imageBytes ->
                    generatedImageRepository.saveGeneratedImage(prompt, imageBytes)
                    reduce { state -> state.copy(isGenerating = false, prompt = "") }
                    emitEffect(GalleryEffect.ImageSaved)
                }
                .onFailure { e ->
                    reduce { it.copy(isGenerating = false) }
                    val msg = e.message?.let { UiText.DynamicString(it) }
                        ?: UiText.StringResource(R.string.gallery_error_generation_failed)
                    emitEffect(GalleryEffect.ShowSnackbar(msg))
                }
        }
    }

    private fun shareImage(imageId: String) {
        val image = _state.value.images.firstOrNull { it.id == imageId } ?: return
        emitEffect(GalleryEffect.ShareImage(imageUrl = image.imageUrl, prompt = image.prompt))
    }

    private fun toggleFavorite(imageId: String) {
        val current = _state.value.images.firstOrNull { it.id == imageId } ?: return
        viewModelScope.launch {
            generatedImageRepository.updateFavorite(imageId, !current.isFavorite)
        }
    }

    private fun reduce(transform: (GalleryState) -> GalleryState) {
        _state.update(transform)
    }

    private fun emitEffect(effect: GalleryEffect) {
        viewModelScope.launch { _effect.emit(effect) }
    }
}
