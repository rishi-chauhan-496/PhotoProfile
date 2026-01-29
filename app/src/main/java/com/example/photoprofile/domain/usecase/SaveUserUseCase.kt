package com.example.photoprofile.domain.usecase

import com.example.photoprofile.domain.model.InsertUserInfo
import com.example.photoprofile.domain.repository.UserRepository

class SaveUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: InsertUserInfo): Boolean {
        return repository.insertUser(user)
    }
}