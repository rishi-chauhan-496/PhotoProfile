package com.example.photoprofile.data.repository

import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import com.example.photoprofile.data.local.DownloadApiService
import com.example.photoprofile.domain.model.DownloadImage
import com.example.photoprofile.domain.repository.ImageDownloadRepository
import java.io.File
import java.io.FileOutputStream

class ImageDownloadRepositoryImpl: ImageDownloadRepository {
    private val apiService = DownloadApiService.fileService

    override suspend fun downloadAndSaveImage(downloadImage: DownloadImage): Boolean {
        try {
            Log.d("main", "Starting Download")

            val picturesDir =
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM
                )

            val appFolder = File(picturesDir, "PhotoProfile")
            if (!appFolder.exists()) appFolder.mkdirs()

            val destinationFile =
                File(appFolder, "${downloadImage.fileId}_${downloadImage.fileName}.jpg")

            val response = apiService.downloadImage(downloadImage.imageUrl)

            if (response.isSuccessful) {

                response.body()?.byteStream()?.use { input ->
                    FileOutputStream(destinationFile).use { output ->
                        input.copyTo(output)
                    }
                }

                MediaScannerConnection.scanFile(
                    downloadImage.context,
                    arrayOf(destinationFile.absolutePath),
                    arrayOf("image/jpeg"),
                    null
                )

                Log.d("main", "Download Success")
                return  true
            } else {
                return false
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}