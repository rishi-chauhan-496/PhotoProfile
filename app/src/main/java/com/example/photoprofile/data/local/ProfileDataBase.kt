package com.example.photoprofile.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper



class ProfileDataBase(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "UserInfo.db"
        private const val DATABASE_VERSION = 1
    }

     object UserInfoTable {
         const val TABLE_NAME = "UserInfo"
         const val ID = "id"
         const val NAME = "name"
         const val EMAIL = "email"
         const val PHOTO = "photo"
         const val HOBBIES = "hobbies"
         const val COUNTRY = "country"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val createUserInfoTable = """
            CREATE TABLE ${UserInfoTable.TABLE_NAME} (
                ${UserInfoTable.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${UserInfoTable.NAME} TEXT NOT NULL,
                ${UserInfoTable.EMAIL} TEXT NOT NULL UNIQUE,
                ${UserInfoTable.PHOTO} TEXT,
                ${UserInfoTable.HOBBIES} TEXT,
                ${UserInfoTable.COUNTRY} TEXT
                );
        """.trimIndent()

        db.execSQL(createUserInfoTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    //Insert Query
    fun insertNewUser(insertUser: InsertUserInfoDto): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(UserInfoTable.NAME, insertUser.name)
        cv.put(UserInfoTable.EMAIL, insertUser.email)
        cv.put(UserInfoTable.PHOTO, insertUser.photo)
        cv.put(UserInfoTable.HOBBIES, insertUser.hobbies)
        cv.put(UserInfoTable.COUNTRY, insertUser.country)

        return db.insert(UserInfoTable.TABLE_NAME, null, cv) > 0
    }

    //update Query
    fun updateUser(user: UserInfoDto): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(UserInfoTable.NAME, user.name)
        cv.put(UserInfoTable.EMAIL, user.email)
        cv.put(UserInfoTable.PHOTO, user.photo)
        cv.put(UserInfoTable.HOBBIES, user.hobbies)
        cv.put(UserInfoTable.COUNTRY, user.country)

        return db.update(UserInfoTable.TABLE_NAME, cv, "${UserInfoTable.ID} = ?", arrayOf(user.id.toString())) > 0
    }

    //Select Query
    fun selectUser(): UserInfoDto? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${UserInfoTable.TABLE_NAME}",null
        )

        var user: UserInfoDto? = null

        if (cursor.moveToFirst()) {
            user = UserInfoDto(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(UserInfoTable.ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(UserInfoTable.NAME)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(UserInfoTable.EMAIL)),
                photo = cursor.getString(cursor.getColumnIndexOrThrow(UserInfoTable.PHOTO)),
                hobbies = cursor.getString(cursor.getColumnIndexOrThrow(UserInfoTable.HOBBIES)),
                country = cursor.getString(cursor.getColumnIndexOrThrow(UserInfoTable.COUNTRY))
            )
        }

        cursor.close()
        return user
    }

}
