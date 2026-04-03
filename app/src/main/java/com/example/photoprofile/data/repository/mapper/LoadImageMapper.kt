package com.example.photoprofile.data.repository.mapper

import com.example.photoprofile.data.local.ImageItemDto
import com.example.photoprofile.domain.model.ImageItem

fun ImageItemDto.toDomain(): ImageItem {
    return ImageItem(
         uri = uri,
         name = name
    )
}