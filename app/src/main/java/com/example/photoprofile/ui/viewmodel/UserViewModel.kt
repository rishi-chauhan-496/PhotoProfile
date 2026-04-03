package com.example.photoprofile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoprofile.domain.usecase.SaveUserUseCase
import com.example.photoprofile.domain.usecase.UpdateUserUseCase
import com.example.photoprofile.ui.dataclass.InsertUserInfoUi
import com.example.photoprofile.ui.dataclass.UserInfoUi
import com.example.photoprofile.ui.dataclass.map.toDomain
import com.example.photoprofile.ui.uistate.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val saveUserUseCase: SaveUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState

    fun onCredentialsChanges(
        name: String,
        email: String,
        hobbies: String,
        country: String
    ) {
        val nameError = if (name.isEmpty()) "Name required" else null
        val emailError = if (email.isEmpty()) "Email required" else null
        val hobbiesError = if (hobbies.isEmpty()) "Hobbies required" else null
        val countryError = if (country.isEmpty()) "Country required" else null

        _uiState.value = _uiState.value.copy(

            nameError = nameError,
            emailError = emailError,
            hobbiesError = hobbiesError,
            countryError = countryError,
            isSavingEnabled = nameError == null && emailError == null && hobbiesError == null && countryError == null
        )
    }

    fun saveUser(
        name: String,
        email: String,
        hobbies: String,
        country: String,
        photo: String
    ) {
        if (!_uiState.value.isSavingEnabled) return

        viewModelScope.launch {
            runCatching {
                saveUserUseCase(
                    InsertUserInfoUi(
                        name = name,
                        email = email,
                        photo = photo,
                        hobbies = hobbies,
                        country = country
                    ).toDomain()
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    error = it.message
                )
            }
        }
    }

    fun updateUser(
        userId: Int,
        name: String,
        email: String,
        hobbies: String,
        country: String,
        photo: String
    ) {
        if (!_uiState.value.isSavingEnabled) return

        viewModelScope.launch {
            runCatching {
                updateUserUseCase(
                    UserInfoUi(
                        id = userId,
                        name = name,
                        email = email,
                        photo = photo,
                        hobbies = hobbies,
                        country = country
                    ).toDomain()
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    error = it.message
                )
            }
        }
    }
}

