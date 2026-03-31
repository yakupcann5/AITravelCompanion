package com.travel.companion.feature.gallery

import com.travel.companion.core.model.GalleryImage

/**
 * Galeri ekrani UI durumu.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
data class GalleryState(
    val images: List<GalleryImage> = emptyList(),
    val prompt: String = "",
    val isLoading: Boolean = false,
    val isGenerating: Boolean = false,
    val selectedImageId: String? = null,
    val error: String? = null,
) {
    val canGenerate: Boolean get() = prompt.isNotBlank() && !isGenerating
    val selectedImage: GalleryImage? get() = images.firstOrNull { it.id == selectedImageId }
}

val sampleGalleryImages = listOf(
    GalleryImage(id = "1", prompt = "Istanbul Bogazici gun batimi", imageUrl = "https://images.unsplash.com/photo-1524231757912-21f4fe3a7200"),
    GalleryImage(id = "2", prompt = "Paris Eyfel Kulesi gece", imageUrl = "https://images.unsplash.com/photo-1502602898657-3e91760cbb34"),
    GalleryImage(id = "3", prompt = "Tokyo sakura mevsimi", imageUrl = "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf"),
    GalleryImage(id = "4", prompt = "Roma Kolezyum", imageUrl = "https://images.unsplash.com/photo-1552832230-c0197dd311b5"),
    GalleryImage(id = "5", prompt = "Barcelona Sagrada Familia", imageUrl = "https://images.unsplash.com/photo-1583422409516-2895a77efded"),
    GalleryImage(id = "6", prompt = "Dubai gece silueti", imageUrl = "https://images.unsplash.com/photo-1512453979798-5ea266f8880c"),
)
