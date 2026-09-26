package com.example.photoprofile.domain.model

import android.content.Context

data class DownloadImage(
    val context: Context,
    val imageUrl: String,
    val fileId: Long,
    val fileName: String
)
