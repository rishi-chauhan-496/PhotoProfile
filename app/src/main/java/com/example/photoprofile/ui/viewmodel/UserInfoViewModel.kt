package com.example.photoprofile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoprofile.domain.usecase.GetUserUseCase
import com.example.photoprofile.ui.dataclass.map.toUi
import com.example.photoprofile.ui.uistate.UserInfoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserInfoViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserInfoUiState())
    val uiState: StateFlow<UserInfoUiState> = _uiState

    fun loadUser() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val user = getUserUseCase()
                _uiState.value = _uiState.value.copy(
                        user = user?.toUi(),
                        isLoading = false
                    )
            } catch (e: Exception) {
                _uiState.value = UserInfoUiState(
                    error = e.message
                )
            }
        }
    }
}