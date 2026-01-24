package com.example.photoprofile.domain.usecase

import com.example.photoprofile.domain.model.UserInfo
import com.example.photoprofile.domain.repository.UserRepository

class GetUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): UserInfo? {
        return repository.getUser()
    }
}