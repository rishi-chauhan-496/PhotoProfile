package com.example.photoprofile.ui.dataclass.map


import com.example.photoprofile.domain.model.ImageItem
import com.example.photoprofile.ui.dataclass.ImageItemUi

fun ImageItem.toUi(): ImageItemUi {
    return ImageItemUi(
        uri = uri,
        name = name
    )
}