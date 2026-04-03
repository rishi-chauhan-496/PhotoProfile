package com.example.photoprofile.ui.uistate

import com.example.photoprofile.ui.dataclass.ImageItemUi

data class PhotoLoadUiState(
    val photo: List<ImageItemUi>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)