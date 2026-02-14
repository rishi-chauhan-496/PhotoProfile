package com.example.photoprofile.ui.dataclass.map

import com.example.photoprofile.domain.model.PexelsResponse
import com.example.photoprofile.domain.model.Photo
import com.example.photoprofile.domain.model.Src
import com.example.photoprofile.ui.dataclass.PexelsResponseUi
import com.example.photoprofile.ui.dataclass.PhotoUi
import com.example.photoprofile.ui.dataclass.SrcUi


fun PexelsResponse.toUi(): PexelsResponseUi {
    return PexelsResponseUi(
        page = page,
        perPage = perPage,
        photos = photos.map { it.toUi() },
        nextPage = nextPage
    )
}

fun Photo.toUi(): PhotoUi {
    return PhotoUi(
        id = id,
        width = width,
        height = height,
        url = url,
        photographer = photographer,
        photographerUrl = photographerUrl,
        photographerId = photographerId,
        avgColor = avgColor,
        src = src.toUi(),
        liked = liked,
        alt = alt
    )
}

fun Src.toUi(): SrcUi {
    return SrcUi(
        original = original,
        large2x = large2x,
        large = large,
        medium = medium,
        small = small,
        portrait = portrait,
        landscape = landscape,
        tiny = tiny
    )
}
