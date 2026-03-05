package com.example.photoprofile

import android.net.Uri
import android.os.Environment
import com.example.photoprofile.ui.dataclass.ImageItemUi
import java.io.File

class loadImagesRepository {

    fun loadImages(): List<ImageItemUi> {

        val imageList = mutableListOf<ImageItemUi>()

        val picturesDir =
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM
            )

        val appFolder = File(picturesDir, "PhotoProfile")

        if (appFolder.exists()) {

            appFolder.listFiles()?.forEach { file ->

                if (file.isFile &&
                    (file.extension == "jpg" ||
                            file.extension == "png" ||
                            file.extension == "jpeg")
                ) {

                    imageList.add(
                        ImageItemUi(
                            uri = Uri.fromFile(file),
                            name = file.name
                        )
                    )
                }
            }
        }

        return imageList
    }

}