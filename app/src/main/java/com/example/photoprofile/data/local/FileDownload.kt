package com.example.photoprofile.data.local

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url
import kotlin.jvm.java

interface FileDownload {
    @Streaming
    @GET
    suspend fun downloadImage(@Url fileUrl: String): Response<ResponseBody>
}

object DownloadApiService {

    val fileService: FileDownload by lazy{
        Retrofit.Builder()
            .baseUrl("https://api.pexels.com")
            .build()
            .create(FileDownload::class.java)
    }
}