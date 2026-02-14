package com.example.photoprofile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoprofile.domain.usecase.GetCuratedPhotosUseCase
import com.example.photoprofile.ui.dataclass.map.toUi
import com.example.photoprofile.ui.uistate.PhotosUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhotosViewModel(
    private val getCuratedPhotosUseCase: GetCuratedPhotosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PhotosUiState())
    val uiState: StateFlow<PhotosUiState> = _uiState

    fun loadPhotos() {

        _uiState.value = _uiState.value.copy(
            isLoading = true
        )

        viewModelScope.launch {
            try {
                val response = getCuratedPhotosUseCase()
                _uiState.value = _uiState.value.copy(
                        photos = response.toUi().photos,
                        isLoading = false
                    )

            } catch (e: Exception) {
                _uiState.value = PhotosUiState(
                    error = e.message
                )
            }
        }
    }
}
