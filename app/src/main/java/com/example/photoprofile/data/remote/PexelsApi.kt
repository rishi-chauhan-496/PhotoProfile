package com.example.photoprofile.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface PexelsApi {

    @Headers("Authorization: YSmZ7az3PyO4TK6bhObwSbBSWB6PSmpRO9GBWM7kuyYXAMOarI98EZdh")
    @GET("v1/curated")
    suspend fun getPhotos(): PexelsResponse

}

object RetrofitInstance {

    private const val BASE_URL = "https://api.pexels.com/"

    val api: PexelsApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PexelsApi::class.java)
    }
}