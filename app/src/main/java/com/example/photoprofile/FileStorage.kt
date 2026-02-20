package com.example.photoprofile

import android.content.Context
import java.io.File

object FileUtils {

    private const val FILE_NAME = "data.txt"

    fun writeToFile(context: Context, text: String) {
        val file= File(context.filesDir, FILE_NAME)
        file.appendText(text)
    }
    fun readToFile(context: Context) : String {
        val file = File(context.filesDir, FILE_NAME)
        return if( file.exists() ) {
            file.readText() }
        else
            "file not found"
    }
}