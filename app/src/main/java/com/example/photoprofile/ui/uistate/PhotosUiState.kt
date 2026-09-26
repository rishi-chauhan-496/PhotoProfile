package com.example.photoprofile.ui.uistate

import com.example.photoprofile.ui.dataclass.PhotoUi

data class PhotosUiState(
    val isLoading: Boolean = false,
    val page: Int = 1,
    val perPage: Int = 0,
    val nextPage: String? = null,
    val photos: List<PhotoUi> = emptyList(),
    val error: String? = null
)
