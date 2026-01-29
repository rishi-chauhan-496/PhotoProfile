package com.example.photoprofile.ui.dataclass

open class InsertUserInfoUi(
    val name: String,
    val email: String,
    val photo: String,
    val hobbies: String,
    val country: String
)
class UserInfoUi(
    val id: Int,
    name: String,
    email: String,
    photo: String,
    hobbies: String,
    country: String) : InsertUserInfoUi(name, email, photo, hobbies, country)
