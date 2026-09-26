package com.example.photoprofile.domain.model

data class PexelsResponse(
    val page: Int,
    val perPage: Int,
    val photos: List<Photo>,
    val nextPage: String?
)
data class Photo(
    val id: Long,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographerUrl: String?,
    val photographerId: Long,
    val avgColor: String?,
    val src: Src,
    val liked: Boolean,
    val alt: String
)

data class Src(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String,
)
