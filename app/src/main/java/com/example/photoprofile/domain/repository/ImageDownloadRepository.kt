package com.example.photoprofile.domain.repository

import com.example.photoprofile.domain.model.DownloadImage

interface ImageDownloadRepository {
    suspend fun downloadAndSaveImage(downloadImage: DownloadImage): Boolean
}