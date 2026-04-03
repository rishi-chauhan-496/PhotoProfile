package com.example.photoprofile.ui.dataclass

import android.content.Context

data class DownloadImageUi(
    val context: Context,
    val imageUrl: String,
    val fileId: Long,
    val fileName: String
)