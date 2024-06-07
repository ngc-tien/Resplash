package com.ngc.tien.resplash.data.remote.mapper.user

import com.ngc.tien.resplash.data.remote.model.user.UserResponse
import com.ngc.tien.resplash.modules.core.BaseRefreshListItem
import java.io.Serializable

data class User(
    override val id: String,
    val userName: String,
    val name: String,
    val bio: String,
    val location: String,
    val profileImageMedium: String,
    val profileImageLarge: String,
    val totalLikes: Int,
    val totalCollections: Int,
    val totalPhotos: Int,
) : BaseRefreshListItem(), Serializable {
    companion object {
        fun emptyUser(): User {
            return User(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                0,
                0,
                0
            )
        }
    }
}

fun UserResponse.toItem(): User {
    return User(
        id = id,
        userName = username,
        name = name,
        bio = bio ?: "",
        location = location ?: "",
        profileImageMedium = profileImage.medium,
        profileImageLarge = profileImage.large,
        totalLikes = totalLikes,
        totalCollections = totalCollections,
        totalPhotos = totalPhotos
    )
}