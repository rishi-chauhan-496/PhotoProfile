package com.example.photoprofile.data.repository.mapper

import com.example.photoprofile.data.local.InsertUserInfoDto
import com.example.photoprofile.data.local.UserInfoDto
import com.example.photoprofile.domain.model.InsertUserInfo
import com.example.photoprofile.domain.model.UserInfo

fun InsertUserInfoDto.toDomain(): InsertUserInfo {
    return InsertUserInfo(
        name = name,
        email = email,
        photo = photo,
        hobbies = hobbies,
        country = country
    )
}

fun UserInfoDto.toDomain(): UserInfo {
    return UserInfo(
        id = id,
        name = name,
        email = email,
        photo = photo,
        hobbies = hobbies,
        country = country
    )
}