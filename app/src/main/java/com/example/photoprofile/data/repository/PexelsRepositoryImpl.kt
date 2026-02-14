package com.example.photoprofile.data.repository

import com.example.photoprofile.data.remote.RetrofitInstance
import com.example.photoprofile.data.repository.mapper.toDomain
import com.example.photoprofile.domain.model.PexelsResponse
import com.example.photoprofile.domain.repository.PexelsRepository

class PexelsRepositoryImpl : PexelsRepository {

    override suspend fun getPexelPhotos(page: Int): PexelsResponse {
        val apiCall = RetrofitInstance.api
            .getPhotos(page = page)

        return apiCall.toDomain()
    }
}