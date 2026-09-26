package com.example.photoprofile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoprofile.domain.usecase.LoadImageUseCase
import com.example.photoprofile.ui.dataclass.map.toUi
import com.example.photoprofile.ui.uistate.PhotoLoadUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhotoLoadViewModel(
    private val loadImageUseCase: LoadImageUseCase
): ViewModel()
{
    private val _uiState = MutableStateFlow(PhotoLoadUiState())

    val uiState: StateFlow<PhotoLoadUiState> = _uiState

    fun loadPhotos() {

        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            runCatching {
                loadImageUseCase()
            }. onSuccess { success ->
                _uiState.value = _uiState.value.copy(
                    photo = success.map { it.toUi() },
                    isLoading = false

                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    error = it.message,
                    isLoading = false
                )
            }
        }
    }
}