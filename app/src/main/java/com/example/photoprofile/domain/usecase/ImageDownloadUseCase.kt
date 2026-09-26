package com.example.photoprofile.domain.usecase

import com.example.photoprofile.domain.model.DownloadImage
import com.example.photoprofile.domain.repository.ImageDownloadRepository


class ImageDownloadUseCase(
    private val repository: ImageDownloadRepository
) {
    suspend operator fun invoke(downloadImage: DownloadImage): Boolean {
        return repository.downloadAndSaveImage(downloadImage)
    }
}