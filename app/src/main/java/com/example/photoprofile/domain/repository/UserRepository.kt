package com.example.photoprofile.domain.repository

import com.example.photoprofile.domain.model.InsertUserInfo
import com.example.photoprofile.domain.model.UserInfo

interface UserRepository {

    suspend fun insertUser(user: InsertUserInfo): Boolean

    suspend fun updateUser(user: UserInfo): Boolean

    suspend fun getUser(): UserInfo?
}
