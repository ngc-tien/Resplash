package com.ngc.tien.resplash.modules.photo.detail

data class PhotoDetailItem(
    val id: String,
    val userName: String,
    val userImage: String,
    val location: String,
    val camInfo: String,
    val aperture: String,
    val focalLength: String,
    val shutterSpeed: String,
    val iso: String,
    val width: Int,
    val height: Int,
    val totalViews: Int,
    val totalDownloads: Int,
    val totalLikes: Int,
    val tags: List<String>
)