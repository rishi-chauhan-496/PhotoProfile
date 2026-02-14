package com.example.photoprofile.domain.usecase

import com.example.photoprofile.domain.model.UserInfo
import com.example.photoprofile.domain.repository.UserRepository

open class UpdateUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: UserInfo): Boolean {
        return repository.updateUser(user)
    }
}