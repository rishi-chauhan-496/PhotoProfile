package com.example.photoprofile.data.repository

import com.example.photoprofile.data.remote.RetrofitInstance
import com.example.photoprofile.data.repository.mapper.toDomain
import com.example.photoprofile.domain.model.PexelsResponse
import com.example.photoprofile.domain.repository.PexelsRepository

class PexelsRepositoryImpl : PexelsRepository {

    override suspend fun getPexelPhotos(): PexelsResponse {
        return RetrofitInstance.api
            .getPhotos()
            .toDomain()
    }
}