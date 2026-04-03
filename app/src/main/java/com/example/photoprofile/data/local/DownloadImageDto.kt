package com.example.photoprofile.data.local

import android.content.Context

data class DownloadImageDto(
    val context: Context,
    val imageUrl: String,
    val fileId: Long,
    val fileName: String
)
