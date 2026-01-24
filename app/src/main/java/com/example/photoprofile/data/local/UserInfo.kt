package com.example.photoprofile.data.local


open class InsertUserInfo(
    val name: String,
    val email: String,
    val photo: String,
    val hobbies: String,
    val country: String
)

class UserInfo(
     val id: Int,
     name: String,
     email: String,
     photo: String,
     hobbies: String,
     country: String) : InsertUserInfo(name, email, photo, hobbies, country )

