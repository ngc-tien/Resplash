package com.ngc.tien.resplash.data.remote.model.search

import androidx.annotation.Keep
import com.ngc.tien.resplash.data.remote.model.collection.CollectionsResponse
import com.ngc.tien.resplash.data.remote.model.photo.PhotoResponse
import com.ngc.tien.resplash.data.remote.model.user.UserResponse
import com.squareup.moshi.Json

@Keep
data class SearchPhotosResponse (
    @Json(name = "results")
    val results: List<PhotoResponse>
)

@Keep
data class SearchCollectionsResponse (
    @Json(name = "results")
    val results: List<CollectionsResponse>
)

@Keep
data class SearchUsersResponse (
    @Json(name = "results")
    val results: List<UserResponse>
)

