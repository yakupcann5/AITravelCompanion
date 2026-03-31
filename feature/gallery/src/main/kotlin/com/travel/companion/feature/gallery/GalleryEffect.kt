package com.travel.companion.feature.gallery

import com.travel.companion.core.common.UiText

/**
 * Galeri ekrani tek seferlik olaylar.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
sealed interface GalleryEffect {
    data object ImageSaved : GalleryEffect
    data class ShowSnackbar(val message: UiText) : GalleryEffect
    data class ShareImage(val imageUrl: String, val prompt: String) : GalleryEffect
}
