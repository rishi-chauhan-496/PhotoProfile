package com.example.photoprofile.ui.dataclass

data class PexelsResponseUi(
    val page: Int,
    val perPage: Int,
    val photos: List<PhotoUi>,
    val nextPage: String?
)

data class PhotoUi(
    val id: Long,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographerUrl: String,
    val photographerId: Long,
    val avgColor: String,
    val src: SrcUi,
    val liked: Boolean,
    val alt: String
)

data class SrcUi(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)

