package com.example.photoprofile.domain.usecase

import com.example.photoprofile.domain.model.PexelsResponse
import com.example.photoprofile.domain.repository.PexelsRepository

class GetCuratedPhotosUseCase(
    private val repository: PexelsRepository
) {
    suspend operator fun invoke(): PexelsResponse {
        return repository.getPexelPhotos()
    }
}