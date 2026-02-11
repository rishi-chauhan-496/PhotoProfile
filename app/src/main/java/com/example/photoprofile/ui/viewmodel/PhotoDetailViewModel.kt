package com.example.photoprofile.ui.viewmodel


import androidx.lifecycle.ViewModel
import com.example.photoprofile.ui.uistate.PhotoDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PhotoDetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PhotoDetailUiState())
    val uiState: StateFlow<PhotoDetailUiState> = _uiState

    fun setPhoto(photo: String) {
        _uiState.value = _uiState.value.copy(
            photo = photo
        )
    }

    fun downloadPhoto() {
        _uiState.value = _uiState.value.copy(
            isDownloading = true
        )

        _uiState.value = _uiState.value.copy(
            isDownloading = false,
            downloadSuccess = true
        )
    }
}
