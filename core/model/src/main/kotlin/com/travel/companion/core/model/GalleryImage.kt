package com.travel.companion.core.model

import androidx.compose.runtime.Immutable

/**
 * Uretilen gorsel domain modeli.
 *
 * @author Yakup Can
 * @date 2026-03-31
 */
@Immutable
data class GalleryImage(
    val id: String,
    val prompt: String,
    val imageUrl: String,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
)
