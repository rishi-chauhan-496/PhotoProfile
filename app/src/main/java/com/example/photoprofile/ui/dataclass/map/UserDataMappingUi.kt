package com.example.photoprofile.ui.dataclass.map


import com.example.photoprofile.domain.model.InsertUserInfo
import com.example.photoprofile.domain.model.UserInfo
import com.example.photoprofile.ui.dataclass.InsertUserInfoUi
import com.example.photoprofile.ui.dataclass.UserInfoUi


fun InsertUserInfo.toUi(): InsertUserInfoUi {
    return InsertUserInfoUi(
        name = name,
        email = email,
        photo = photo,
        hobbies = hobbies,
        country = country
    )
}

fun UserInfo.toUi(): UserInfoUi {
    return UserInfoUi(
        id = id,
        name = name,
        email = email,
        photo = photo,
        hobbies = hobbies,
        country = country
    )
}