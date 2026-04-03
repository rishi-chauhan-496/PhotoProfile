package com.example.photoprofile.data.repository

import com.example.photoprofile.data.local.ProfileDataBase
import com.example.photoprofile.data.repository.mapper.toDomain
import com.example.photoprofile.data.repository.mapper.toDto
import com.example.photoprofile.domain.model.InsertUserInfo
import com.example.photoprofile.domain.model.UserInfo
import com.example.photoprofile.domain.repository.UserRepository

class UserRepositoryImpl(
    private val db: ProfileDataBase
) : UserRepository {

    override suspend fun insertUser(user: InsertUserInfo): Boolean {
        return db.insertNewUser(
            user.toDto()
        )
    }

    override suspend fun updateUser(user: UserInfo): Boolean {
        return db.updateUser(
            user.toDto()
        )
    }

    override suspend fun getUser(): UserInfo? {
        return db.selectUser()?.toDomain()
    }
}