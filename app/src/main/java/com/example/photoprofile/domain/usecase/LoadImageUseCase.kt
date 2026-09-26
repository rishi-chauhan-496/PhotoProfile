package com.example.photoprofile.domain.usecase

import com.example.photoprofile.domain.model.ImageItem
import com.example.photoprofile.domain.repository.LoadImagesRepository

class LoadImageUseCase (
    private val repository: LoadImagesRepository
) {
    suspend operator fun invoke(): List<ImageItem> {
        return repository.loadImages()
    }

}