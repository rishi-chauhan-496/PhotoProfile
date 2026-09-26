package com.example.photoprofile.data.repository

import android.net.Uri
import android.os.Environment
import android.util.Log
import com.example.photoprofile.data.local.ImageItemDto
import com.example.photoprofile.data.repository.mapper.toDomain
import com.example.photoprofile.domain.model.ImageItem
import com.example.photoprofile.domain.repository.LoadImagesRepository
import java.io.File

class LoadImagesRepositoryImpl: LoadImagesRepository {

    override suspend fun loadImages(): List<ImageItem> {

        val imageList = mutableListOf<ImageItem>()

        val picturesDir =
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM
            )

        val appFolder = File(picturesDir, "PhotoProfile")

        if (appFolder.exists()) {

            appFolder.listFiles()?.forEach { file ->

                if (file.isFile)
                 {
                    imageList.add(
                        ImageItemDto(
                            uri = Uri.fromFile(file),
                            name = file.name
                        ).toDomain()
                    )
                }
            }
        }

        Log.d("main","${imageList.size}")

        return imageList
    }

}