package com.example.photoprofile.data.remote

data class PexelsResponseDto(
    val page: Int,
    val perPage: Int,
    val photos: List<PhotoDto>,
    val nextPage: String?
)
data class PhotoDto(
    val id: Long,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographerUrl: String,
    val photographerId: Long,
    val avgColor: String,
    val src: SrcDto,
    val liked: Boolean,
    val alt: String
)


data class SrcDto(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String,
)
