package com.ngc.tien.resplash.data.remote.model


import com.squareup.moshi.Json
import androidx.annotation.Keep

@Keep
data class PhotoDetailResponse(
    @Json(name = "id")
    val id: String, // Homxb0oO8RA
    @Json(name = "width")
    val width: Int, // 4000
    @Json(name = "height")
    val height: Int, // 6000
    @Json(name = "color")
    val color: String, // #260c0c
    @Json(name = "urls")
    val urls: Urls,
    @Json(name = "links")
    val links: Links,
    @Json(name = "likes")
    val likes: Int, // 12
    @Json(name = "user")
    val user: User,
    @Json(name = "exif")
    val exif: Exif,
    @Json(name = "location")
    val location: Location,
    @Json(name = "tags")
    val tags: List<Tag>,
    @Json(name = "views")
    val views: Int, // 223
    @Json(name = "downloads")
    val downloads: Int, // 3
) {
    @Keep
    data class Urls(
        @Json(name = "raw")
        val raw: String, // https://images.unsplash.com/photo-1716565679284-f2dd89f4313e?ixid=M3w2MTQ2Njh8MHwxfGFsbHx8fHx8fHx8fDE3MTY3MzExMzB8&ixlib=rb-4.0.3
        @Json(name = "full")
        val full: String, // https://images.unsplash.com/photo-1716565679284-f2dd89f4313e?crop=entropy&cs=srgb&fm=jpg&ixid=M3w2MTQ2Njh8MHwxfGFsbHx8fHx8fHx8fDE3MTY3MzExMzB8&ixlib=rb-4.0.3&q=85
        @Json(name = "regular")
        val regular: String, // https://images.unsplash.com/photo-1716565679284-f2dd89f4313e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2MTQ2Njh8MHwxfGFsbHx8fHx8fHx8fDE3MTY3MzExMzB8&ixlib=rb-4.0.3&q=80&w=1080
        @Json(name = "small")
        val small: String, // https://images.unsplash.com/photo-1716565679284-f2dd89f4313e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2MTQ2Njh8MHwxfGFsbHx8fHx8fHx8fDE3MTY3MzExMzB8&ixlib=rb-4.0.3&q=80&w=400
        @Json(name = "thumb")
        val thumb: String, // https://images.unsplash.com/photo-1716565679284-f2dd89f4313e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2MTQ2Njh8MHwxfGFsbHx8fHx8fHx8fDE3MTY3MzExMzB8&ixlib=rb-4.0.3&q=80&w=200
        @Json(name = "small_s3")
        val smallS3: String // https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1716565679284-f2dd89f4313e
    )

    @Keep
    data class Links(
        @Json(name = "self")
        val self: String, // https://api.unsplash.com/photos/a-chinese-restaurant-with-neon-signs-on-the-side-of-it-Homxb0oO8RA
        @Json(name = "html")
        val html: String, // https://unsplash.com/photos/a-chinese-restaurant-with-neon-signs-on-the-side-of-it-Homxb0oO8RA
        @Json(name = "download")
        val download: String, // https://unsplash.com/photos/Homxb0oO8RA/download?ixid=M3w2MTQ2Njh8MHwxfGFsbHx8fHx8fHx8fDE3MTY3MzExMzB8
        @Json(name = "download_location")
        val downloadLocation: String // https://api.unsplash.com/photos/Homxb0oO8RA/download?ixid=M3w2MTQ2Njh8MHwxfGFsbHx8fHx8fHx8fDE3MTY3MzExMzB8
    )

    @Keep
    data class User(
        @Json(name = "id")
        val id: String, // YSTZkkK3V1o
        @Json(name = "name")
        val name: String, // mos design
        @Json(name = "profile_image")
        val profileImage: ProfileImage,
    ) {
        @Keep
        data class ProfileImage(
            @Json(name = "small")
            val small: String, // https://images.unsplash.com/profile-1664189090215-f1cd1a693fbbimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32
            @Json(name = "medium")
            val medium: String, // https://images.unsplash.com/profile-1664189090215-f1cd1a693fbbimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64
            @Json(name = "large")
            val large: String // https://images.unsplash.com/profile-1664189090215-f1cd1a693fbbimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128
        )
    }

    @Keep
    data class Exif(
        @Json(name = "make")
        val make: String?, // SONY
        @Json(name = "model")
        val model: String?, // ILCE-7M3
        @Json(name = "name")
        val name: String?, // SONY, ILCE-7M3
        @Json(name = "exposure_time")
        val exposureTime: String?, // 1/125
        @Json(name = "aperture")
        val aperture: String?, // 2.8
        @Json(name = "focal_length")
        val focalLength: String?, // 28.0
        @Json(name = "iso")
        val iso: Int? // 100
    )

    @Keep
    data class Location(
        @Json(name = "name")
        val name: String?, // Tokyo Shinbashi, Japan
        @Json(name = "city")
        val city: String?, // null
        @Json(name = "country")
        val country: String?, // null
        @Json(name = "position")
        val position: Position
    ) {
        @Keep
        data class Position(
            @Json(name = "latitude")
            val latitude: Double?, // 0.0
            @Json(name = "longitude")
            val longitude: Double? // 0.0
        )
    }

    @Keep
    data class Tag(
        @Json(name = "type")
        val type: String, // search
        @Json(name = "title")
        val title: String, // japan
    )
}