package com.example.photoprofile.data.repository.mapper

import com.example.photoprofile.data.remote.PexelsResponseDto
import com.example.photoprofile.data.remote.PhotoDto
import com.example.photoprofile.data.remote.SrcDto
import com.example.photoprofile.domain.model.PexelsResponse
import com.example.photoprofile.domain.model.Photo
import com.example.photoprofile.domain.model.Src

fun PexelsResponseDto.toDomain(): PexelsResponse {
    return PexelsResponse(
        page = page,
        perPage = perPage,
        photos = photos.map { it.toDomain() },
        nextPage = nextPage
    )
}

fun PhotoDto.toDomain(): Photo {
    return Photo(
        id = id,
        width = width,
        height = height,
        url = url,
        photographer = photographer,
        photographerUrl = photographerUrl,
        photographerId = photographerId,
        avgColor = avgColor,
        src = src.toDomain(),
        liked = liked,
        alt = alt
    )
}

fun SrcDto.toDomain(): Src {
    return Src(
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