package com.example.photoprofile.data.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProfileDataBase(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "User.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {

        val createUserInfoTable = """
            CREATE TABLE UserInfo (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                photo TEXT,
                hobbies TEXT,
                country TEXT
                );
        """.trimIndent()

        db.execSQL(createUserInfoTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    //Insert Query
    fun insertNewUser(name: String, email: String, photo: String, hobbies: String, country: String): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put("name", name)
        cv.put("email", email)
        cv.put("photo", photo)
        cv.put("hobbies", hobbies)
        cv.put("country", country)

        return db.insert("UserInfo", null, cv) > 0
    }

    //update Query
    fun updateUser(id: Int, name: String, email: String, photo: String?, hobbies: String?, country: String?): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put("name", name)
        cv.put("email", email)
        cv.put("photo", photo)
        cv.put("hobbies", hobbies)
        cv.put("country", country)

        return db.update("UserInfo", cv, "id = ?", arrayOf(id.toString())) > 0
    }

    //Select Query
    fun selectUser(id: Int): UserInfo? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM UserInfo WHERE id = ?",
            arrayOf(id.toString())
        )

        var user: UserInfo? = null

        if (cursor.moveToFirst()) {
            user = UserInfo(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                photo = cursor.getString(cursor.getColumnIndexOrThrow("photo")),
                hobbies = cursor.getString(cursor.getColumnIndexOrThrow("hobbies")),
                country = cursor.getString(cursor.getColumnIndexOrThrow("country"))
            )
        }

        cursor.close()
        return user
    }

    data class UserInfo(
        val id: Int,
        val name: String,
        val email: String,
        val photo: String,
        val hobbies: String,
        val country: String
    )
}
