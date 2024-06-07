package com.ngc.tien.resplash.data.remote.model.collection


import androidx.annotation.Keep
import com.ngc.tien.resplash.data.remote.model.photo.PhotoResponse
import com.ngc.tien.resplash.data.remote.model.photo.Tag
import com.ngc.tien.resplash.data.remote.model.user.UserResponse
import com.squareup.moshi.Json

@Keep
data class CollectionsResponse(
    @Json(name = "id")
    val id: String, // tLDcyvOC_ks
    @Json(name = "title")
    val title: String, // American Cities
    @Json(name = "description")
    val description: String?, // Discover the raw beauty of new motherhood with this collection.  Capturing tender moments and the profound bond between a mother and child.
    @Json(name = "published_at")
    val publishedAt: String, // 2024-05-22T15:02:10Z
    @Json(name = "last_collected_at")
    val lastCollectedAt: String, // 2024-05-22T15:02:05Z
    @Json(name = "updated_at")
    val updatedAt: String, // 2024-05-22T15:02:11Z
    @Json(name = "featured")
    val featured: Boolean, // true
    @Json(name = "total_photos")
    val totalPhotos: Int, // 54
    @Json(name = "private")
    val `private`: Boolean, // false
    @Json(name = "share_key")
    val shareKey: String, // 9f4a4d2e5fdd0f19a7450c7da8535f1e
    @Json(name = "tags")
    val tags: List<Tag>,
    @Json(name = "links")
    val links: Links,
    @Json(name = "user")
    val user: UserResponse,
    @Json(name = "cover_photo")
    val coverPhoto: PhotoResponse,
    @Json(name = "preview_photos")
    val previewPhotos: List<PhotoResponse>
) {
    @Keep
    data class Links(
        @Json(name = "self")
        val self: String, // https://api.unsplash.com/collections/tLDcyvOC_ks
        @Json(name = "html")
        val html: String, // https://unsplash.com/collections/tLDcyvOC_ks/american-cities
        @Json(name = "photos")
        val photos: String, // https://api.unsplash.com/collections/tLDcyvOC_ks/photos
        @Json(name = "related")
        val related: String // https://api.unsplash.com/collections/tLDcyvOC_ks/related
    )

}