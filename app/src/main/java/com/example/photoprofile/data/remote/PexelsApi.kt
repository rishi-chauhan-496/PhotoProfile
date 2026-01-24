package com.example.photoprofile.data.remote

import com.example.photoprofile.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface PexelsApi {

    @Headers("Authorization: ${BuildConfig.MY_SECRET_API_KEY}")
    @GET("v1/curated")
    suspend fun getPhotos(): PexelsResponseDto

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