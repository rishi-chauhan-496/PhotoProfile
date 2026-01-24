package com.example.photoprofile.data.repository

import com.example.photoprofile.data.local.InsertUserInfoDto
import com.example.photoprofile.data.local.ProfileDataBase
import com.example.photoprofile.data.local.UserInfoDto
import com.example.photoprofile.data.repository.mapper.toDomain
import com.example.photoprofile.domain.model.InsertUserInfo
import com.example.photoprofile.domain.model.UserInfo
import com.example.photoprofile.domain.repository.UserRepository

class UserRepositoryImpl(
    private val db: ProfileDataBase
) : UserRepository {

    override suspend fun insertUser(user: InsertUserInfo): Boolean {
        return db.insertNewUser(
            InsertUserInfoDto(
                name = user.name,
                email = user.email,
                photo = user.photo,
                hobbies = user.hobbies,
                country = user.country
            )
        )
    }

    override suspend fun updateUser(user: UserInfo): Boolean {
        return db.updateUser(
            UserInfoDto(
                id = user.id,
                name = user.name,
                email = user.email,
                photo = user.photo,
                hobbies = user.hobbies,
                country = user.country
            )
        )
    }

    override suspend fun getUser(): UserInfo? {
        return db.selectUser()?.toDomain()
    }
}