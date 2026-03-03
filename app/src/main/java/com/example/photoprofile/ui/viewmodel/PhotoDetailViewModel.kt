package com.example.photoprofile.ui.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoprofile.ImageDownloadRepository
import com.example.photoprofile.ui.uistate.PhotoDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhotoDetailViewModel(
    private val repository: ImageDownloadRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PhotoDetailUiState())
    val uiState: StateFlow<PhotoDetailUiState> = _uiState

    fun downloadPhoto(context: Context, imageUrl: String,imageId: Long) {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isDownloading = true,
                downloadSuccess = false
            )

            val success = repository.downloadAndSaveImage(
                context,
                imageUrl,
                imageId
            )

            _uiState.value = _uiState.value.copy(
                isDownloading = false,
                downloadSuccess = success,
                error = if (!success) "Download failed" else null
            )
        }
    }
}
