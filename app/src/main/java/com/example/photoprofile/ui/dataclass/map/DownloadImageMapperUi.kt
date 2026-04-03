package com.example.photoprofile.ui.dataclass.map

import com.example.photoprofile.domain.model.DownloadImage
import com.example.photoprofile.ui.dataclass.DownloadImageUi


fun DownloadImageUi.toDomain(): DownloadImage {
    return DownloadImage(
        context = context,
        imageUrl =imageUrl,
        fileId = fileId,
        fileName = fileName
    )
}