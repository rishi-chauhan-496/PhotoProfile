package com.example.photoprofile.ui.uistate

import com.example.photoprofile.ui.dataclass.PhotoUi

data class PhotosUiState(
    val isLoading: Boolean = false,
    val photos: List<PhotoUi> = emptyList(),
    val error: String? = null
)
