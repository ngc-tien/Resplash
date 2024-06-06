package com.ngc.tien.resplash.data.remote.mapper.collection

import com.ngc.tien.resplash.data.remote.model.collection.CollectionsResponse
import com.ngc.tien.resplash.modules.core.BaseRefreshListItem
import java.io.Serializable

data class Collection(
    override val id: String,
    val title: String,
    val userName: String,
    val userImage: String,
    val totalPhotos: Int,
    val coverWidth: Int,
    val coverHeight: Int,
    val coverUrl: String,
    val coverColor: String,
    val linkHtml: String,
) : BaseRefreshListItem(), Serializable

fun CollectionsResponse.toItem(): Collection {
    return Collection(
        id = id,
        title = title,
        totalPhotos = totalPhotos,
        userName = user.name,
        userImage = user.profileImage.medium,
        coverWidth = coverPhoto?.width ?: 0,
        coverHeight = coverPhoto?.height ?: 0,
        coverUrl = coverPhoto.urls.regular,
        coverColor = coverPhoto.color ?: "#E0E0E0",
        linkHtml = links.html
    )
}