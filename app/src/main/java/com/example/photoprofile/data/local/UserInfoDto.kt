package com.example.photoprofile.data.local


open class InsertUserInfoDto(
    val name: String,
    val email: String,
    val photo: String,
    val hobbies: String,
    val country: String
)

class UserInfoDto(
     val id: Int,
     name: String,
     email: String,
     photo: String,
     hobbies: String,
     country: String) : InsertUserInfoDto(name, email, photo, hobbies, country )