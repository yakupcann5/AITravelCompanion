package com.travel.companion.core.data.mapper

import com.travel.companion.core.database.entity.GeneratedImageEntity
import com.travel.companion.core.model.GalleryImage

/**
 * GeneratedImage entity-domain donusturucu.
 *
 * @author Yakup Can
 * @date 2026-03-31
 */
fun GeneratedImageEntity.toDomain(): GalleryImage = GalleryImage(
    id = id,
    prompt = prompt,
    imageUrl = imageUrl,
    isFavorite = isFavorite,
    createdAt = createdAt,
)

fun GalleryImage.toEntity(): GeneratedImageEntity = GeneratedImageEntity(
    id = id,
    prompt = prompt,
    imageUrl = imageUrl,
    isFavorite = isFavorite,
    createdAt = createdAt,
)
