package com.example.photoprofile.ui.uistate

import com.example.photoprofile.ui.dataclass.InsertUserInfoUi

data class UserUiState(

    val nameError: String? = null,
    val emailError: String? = null,
    val hobbiesError: String? = null,
    val countryError: String? = null,

    val isLoading: Boolean = false,
    val user: InsertUserInfoUi? = null,
    val isSavingEnabled: Boolean = false,
    val error: String? = null
)