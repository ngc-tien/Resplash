package com.ngc.tien.resplash.modules.collections

data class CollectionItem(
    val id: String,
    val title: String,
    val userName: String,
    val userImage: String,
    val totalPhotos: Int,
    val coverWidth: Int,
    val coverHeight: Int,
    val coverUrl: String,
    val coverColor: String,
)
