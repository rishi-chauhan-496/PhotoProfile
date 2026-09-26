package com.example.photoprofile.domain.repository

import com.example.photoprofile.domain.model.ImageItem

interface LoadImagesRepository {
    suspend fun loadImages(): List<ImageItem>
}