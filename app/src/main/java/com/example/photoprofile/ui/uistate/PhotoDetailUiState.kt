package com.example.photoprofile.ui.uistate

data class PhotoDetailUiState(
    val photo: String? = null,
    val isDownloading: Boolean = false,
    val downloadSuccess: Boolean = false,
    val error: String? = null
)
