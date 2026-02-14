package com.example.photoprofile.ui.uistate

import com.example.photoprofile.ui.dataclass.UserInfoUi

data class UserInfoUiState(
    val isLoading: Boolean = false,
    val user: UserInfoUi? = null,
    val error: String? = null
)
