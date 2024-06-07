package com.ngc.tien.resplash.data.remote.mapper.collection

import com.ngc.tien.resplash.data.remote.mapper.user.User
import com.ngc.tien.resplash.data.remote.mapper.user.toItem
import com.ngc.tien.resplash.data.remote.model.collection.CollectionsResponse
import com.ngc.tien.resplash.modules.core.BaseRefreshListItem
import java.io.Serializable

data class Collection(
    override val id: String,
    val title: String,
    val user: User,
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
        user = user.toItem(),
        coverWidth = coverPhoto?.width ?: 0,
        coverHeight = coverPhoto?.height ?: 0,
        coverUrl = coverPhoto.urls.regular,
        coverColor = coverPhoto.color ?: "#E0E0E0",
        linkHtml = links.html
    )
}