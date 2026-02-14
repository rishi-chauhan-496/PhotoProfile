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


    private var currentPage = 1
    private var isLoadingMore = false
    private var isLastPage = false


    fun loadPhotos() {

        if (isLoadingMore || isLastPage) return
            isLoadingMore = true

        _uiState.value = _uiState.value.copy(isLoading = currentPage == 1)

        viewModelScope.launch {
            try {
                val response = getCuratedPhotosUseCase(currentPage)

                val uiResponse = response.toUi()

                if (uiResponse.nextPage == null) {
                    isLastPage = true
                } else {
                    currentPage++
                }

                _uiState.value = _uiState.value.copy(
                    photos = _uiState.value.photos + uiResponse.photos,
                    page = uiResponse.page,
                    perPage = uiResponse.perPage,
                    nextPage = uiResponse.nextPage,
                    isLoading = false
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message
                )
            }
            isLoadingMore = false
        }
    }
}
