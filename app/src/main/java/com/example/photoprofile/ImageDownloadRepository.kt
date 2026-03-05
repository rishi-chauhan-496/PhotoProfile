package com.example.photoprofile

import android.content.Context
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import com.example.photoprofile.data.local.DownloadApiService
import java.io.File
import java.io.FileOutputStream

class ImageDownloadRepository {

    private val apiService = DownloadApiService.fileService

    suspend fun downloadAndSaveImage(
        context: Context,
        imageUrl: String,
        fileId: Long,
        fileName: String
    ): Boolean  {

        try {
            Log.d("main", "Starting Download")

            val picturesDir =
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM
                )

            val appFolder = File(picturesDir, "PhotoProfile")
            if (!appFolder.exists()) appFolder.mkdirs()

            val destinationFile =
                File(appFolder, "${fileId}_$fileName.jpg")

            val response = apiService.downloadImage(imageUrl)

            if (response.isSuccessful) {

                response.body()?.byteStream()?.use { input ->
                    FileOutputStream(destinationFile).use { output ->
                        input.copyTo(output)
                    }
                }

                MediaScannerConnection.scanFile(
                    context,
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