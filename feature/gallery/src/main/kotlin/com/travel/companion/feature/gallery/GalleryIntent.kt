package com.travel.companion.feature.gallery

/**
 * Galeri ekrani kullanici aksiyonlari.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
sealed interface GalleryIntent {
    data object LoadImages : GalleryIntent
    data class PromptChanged(val prompt: String) : GalleryIntent
    data object GenerateImage : GalleryIntent
    data class ToggleFavorite(val imageId: String) : GalleryIntent
    data class SelectImage(val imageId: String) : GalleryIntent
    data object ClearSelection : GalleryIntent
    data class ShareImage(val imageId: String) : GalleryIntent
}
