package com.example.photoprofile.ui.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoprofile.domain.usecase.ImageDownloadUseCase
import com.example.photoprofile.ui.dataclass.DownloadImageUi
import com.example.photoprofile.ui.dataclass.map.toDomain
import com.example.photoprofile.ui.uistate.PhotoDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class PhotoDetailViewModel(
    private val imageDownloadUseCase: ImageDownloadUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PhotoDetailUiState())
    val uiState: StateFlow<PhotoDetailUiState> = _uiState

    fun downloadPhoto(
        context: Context,
        imageUrl: String,
        imageId: Long,
        imageType: String
    ) {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isDownloading = true,
                error = null
            )

            runCatching {
                imageDownloadUseCase(
                    DownloadImageUi(
                        context = context,
                        imageUrl = imageUrl,
                        fileId = imageId,
                        fileName = imageType
                    ).toDomain()
                )
            }.onSuccess { success ->

                _uiState.value = _uiState.value.copy(
                    isDownloading = false,
                    downloadSuccess = success
                )

            }.onFailure { throwable ->

                _uiState.value = _uiState.value.copy(
                    isDownloading = false,
                    error = throwable.message
                )
            }
        }
    }
}

