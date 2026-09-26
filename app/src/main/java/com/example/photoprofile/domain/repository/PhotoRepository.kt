package com.example.photoprofile.domain.repository

import com.example.photoprofile.domain.model.PexelsResponse


interface PexelsRepository {
    suspend fun getPexelPhotos(page: Int): PexelsResponse
}