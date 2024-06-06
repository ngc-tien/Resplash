package com.ngc.tien.resplash.data.remote.mapper.photo

import com.ngc.tien.resplash.data.remote.model.photo.PhotoResponse
import com.ngc.tien.resplash.modules.core.BaseRefreshListItem
import java.io.Serializable

data class Photo(
    override val id: String,
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
    val color: String,
    val thumbnailUrl: String,
    val downloadPhotoUrl: String,
    val linkHtml: String,
    val totalViews: Int,
    val totalDownloads: Int,
    val totalLikes: Int,
    val tags: List<String>
) : BaseRefreshListItem(), Serializable

fun PhotoResponse.toItem(): Photo {
    return Photo(
        id = id,
        userName = user?.name ?: "",
        userImage = user?.profileImage?.medium ?: "",
        location = location?.name ?: "",
        camInfo = exif?.name ?: "Unknown",
        aperture = exif?.aperture ?: "Unknown",
        focalLength = exif?.focalLength ?: "Unknown",
        shutterSpeed = exif?.exposureTime ?: "Unknown",
        iso = exif?.iso?.toString() ?: "Unknown",
        width = width ?: 0,
        height = height ?: 0,
        thumbnailUrl = urls.regular,
        downloadPhotoUrl = urls.full,
        color = color ?: "#E0E0E0",
        totalViews = views ?: 0,
        totalDownloads = downloads ?: 0,
        totalLikes = likes ?: 0,
        linkHtml = links?.html ?: "",
        tags = tags?.map {
            it.title
        } ?: emptyList()
    )
}