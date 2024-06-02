package com.ngc.tien.resplash.data.remote.model


import androidx.annotation.Keep
import com.squareup.moshi.Json
import java.io.Serializable

@Keep
data class PhotoResponseItem(
    @Json(name = "id")
    val id: String, // Ga7aBzN7qDw
    @Json(name = "slug")
    val slug: String, // green-wooden-door-on-gray-concrete-floor-Ga7aBzN7qDw
    @Json(name = "alternative_slugs")
    val alternativeSlugs: AlternativeSlugs,
    @Json(name = "created_at")
    val createdAt: String, // 2021-01-11T19:03:43Z
    @Json(name = "updated_at")
    val updatedAt: String, // 2024-05-25T10:22:02Z
    @Json(name = "promoted_at")
    val promotedAt: String?, // 2024-05-25T09:11:23Z
    @Json(name = "width")
    val width: Int, // 3600
    @Json(name = "height")
    val height: Int, // 4199
    @Json(name = "color")
    val color: String, // #8ca626
    @Json(name = "blur_hash")
    val blurHash: String?, // LPG]7-xtrzt603afofay=%WESuay
    @Json(name = "description")
    val description: String?, // A minimalistic image of a single white anemone (wild swan) flower, with a yellow centre, in a white vase, set against a yellow background.
    @Json(name = "alt_description")
    val altDescription: String?, // green wooden door on gray concrete floor
    @Json(name = "breadcrumbs")
    val breadcrumbs: List<Any>,
    @Json(name = "urls")
    val urls: Urls,
    @Json(name = "links")
    val links: Links,
    @Json(name = "likes")
    val likes: Int, // 59
    @Json(name = "liked_by_user")
    val likedByUser: Boolean, // false
    @Json(name = "current_user_collections")
    val currentUserCollections: List<Any>,
    @Json(name = "sponsorship")
    val sponsorship: Any?, // null
    @Json(name = "topic_submissions")
    val topicSubmissions: TopicSubmissions?,
    @Json(name = "asset_type")
    val assetType: String, // photo
    @Json(name = "user")
    val user: User
) : Serializable {
    @Keep
    data class AlternativeSlugs(
        @Json(name = "en")
        val en: String, // green-wooden-door-on-gray-concrete-floor-Ga7aBzN7qDw
        @Json(name = "es")
        val es: String, // puerta-de-madera-verde-sobre-suelo-de-hormigon-gris-Ga7aBzN7qDw
        @Json(name = "ja")
        val ja: String, // 灰色のコンクリートの床に緑の木製のドア-Ga7aBzN7qDw
        @Json(name = "fr")
        val fr: String, // porte-en-bois-vert-sur-sol-en-beton-gris-Ga7aBzN7qDw
        @Json(name = "it")
        val `it`: String, // porta-in-legno-verde-su-pavimento-in-cemento-grigio-Ga7aBzN7qDw
        @Json(name = "ko")
        val ko: String, // 회색-콘크리트-바닥에-녹색-나무-문-Ga7aBzN7qDw
        @Json(name = "de")
        val de: String, // grune-holztur-auf-grauem-betonboden-Ga7aBzN7qDw
        @Json(name = "pt")
        val pt: String // porta-de-madeira-verde-no-piso-de-concreto-cinzento-Ga7aBzN7qDw
    )

    @Keep
    data class Urls(
        @Json(name = "raw")
        val raw: String, // https://images.unsplash.com/photo-1610391547035-b4541ce32da0?ixid=M3w2MTQ2Njh8MHwxfGFsbHwxfHx8fHx8Mnx8MTcxNjYzNTQ0MHw&ixlib=rb-4.0.3
        @Json(name = "full")
        val full: String, // https://images.unsplash.com/photo-1610391547035-b4541ce32da0?crop=entropy&cs=srgb&fm=jpg&ixid=M3w2MTQ2Njh8MHwxfGFsbHwxfHx8fHx8Mnx8MTcxNjYzNTQ0MHw&ixlib=rb-4.0.3&q=85
        @Json(name = "regular")
        val regular: String, // https://images.unsplash.com/photo-1610391547035-b4541ce32da0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2MTQ2Njh8MHwxfGFsbHwxfHx8fHx8Mnx8MTcxNjYzNTQ0MHw&ixlib=rb-4.0.3&q=80&w=1080
        @Json(name = "small")
        val small: String, // https://images.unsplash.com/photo-1610391547035-b4541ce32da0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2MTQ2Njh8MHwxfGFsbHwxfHx8fHx8Mnx8MTcxNjYzNTQ0MHw&ixlib=rb-4.0.3&q=80&w=400
        @Json(name = "thumb")
        val thumb: String, // https://images.unsplash.com/photo-1610391547035-b4541ce32da0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2MTQ2Njh8MHwxfGFsbHwxfHx8fHx8Mnx8MTcxNjYzNTQ0MHw&ixlib=rb-4.0.3&q=80&w=200
        @Json(name = "small_s3")
        val smallS3: String // https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1610391547035-b4541ce32da0
    )

    @Keep
    data class Links(
        @Json(name = "self")
        val self: String, // https://api.unsplash.com/photos/green-wooden-door-on-gray-concrete-floor-Ga7aBzN7qDw
        @Json(name = "html")
        val html: String, // https://unsplash.com/photos/green-wooden-door-on-gray-concrete-floor-Ga7aBzN7qDw
        @Json(name = "download")
        val download: String, // https://unsplash.com/photos/Ga7aBzN7qDw/download?ixid=M3w2MTQ2Njh8MHwxfGFsbHwxfHx8fHx8Mnx8MTcxNjYzNTQ0MHw
        @Json(name = "download_location")
        val downloadLocation: String // https://api.unsplash.com/photos/Ga7aBzN7qDw/download?ixid=M3w2MTQ2Njh8MHwxfGFsbHwxfHx8fHx8Mnx8MTcxNjYzNTQ0MHw
    )

    @Keep
    data class TopicSubmissions(
        @Json(name = "wallpapers")
        val wallpapers: Wallpapers?,
        @Json(name = "textures-patterns")
        val texturesPatterns: TexturesPatterns?,
        @Json(name = "architecture-interior")
        val architectureInterior: ArchitectureInterior?,
        @Json(name = "nature")
        val nature: Nature?,
        @Json(name = "experimental")
        val experimental: Experimental?,
        @Json(name = "3d-renders")
        val dRenders: DRenders?
    ) {
        @Keep
        data class Wallpapers(
            @Json(name = "status")
            val status: String // rejected
        )

        @Keep
        data class TexturesPatterns(
            @Json(name = "status")
            val status: String // rejected
        )

        @Keep
        data class ArchitectureInterior(
            @Json(name = "status")
            val status: String, // approved
            @Json(name = "approved_on")
            val approvedOn: String? // 2024-05-03T17:05:01Z
        )

        @Keep
        data class Nature(
            @Json(name = "status")
            val status: String // unevaluated
        )

        @Keep
        data class Experimental(
            @Json(name = "status")
            val status: String // unevaluated
        )

        @Keep
        data class DRenders(
            @Json(name = "status")
            val status: String, // approved
            @Json(name = "approved_on")
            val approvedOn: String? // 2024-05-20T08:37:48Z
        )
    }

    @Keep
    data class User(
        @Json(name = "id")
        val id: String, // PN0aWWIssaM
        @Json(name = "updated_at")
        val updatedAt: String, // 2024-05-25T09:11:23Z
        @Json(name = "username")
        val username: String, // jakenackos
        @Json(name = "name")
        val name: String, // Jake Nackos
        @Json(name = "first_name")
        val firstName: String, // Jake
        @Json(name = "last_name")
        val lastName: String?, // Nackos
        @Json(name = "twitter_username")
        val twitterUsername: String?, // JakeNackos
        @Json(name = "portfolio_url")
        val portfolioUrl: String?, // http://jakenackos.com
        @Json(name = "bio")
        val bio: String?, // If you use any photos and would like to buy me a coffee as a "thankyou", you can here! 👉🏼 https://www.buymeacoffee.com/jakenackos or on Instagram: @jakenackos
        @Json(name = "location")
        val location: String?, // Salt Lake City, Utah
        @Json(name = "links")
        val links: Links,
        @Json(name = "profile_image")
        val profileImage: ProfileImage,
        @Json(name = "instagram_username")
        val instagramUsername: String?, // jakenackos
        @Json(name = "total_collections")
        val totalCollections: Int, // 17
        @Json(name = "total_likes")
        val totalLikes: Int, // 0
        @Json(name = "total_photos")
        val totalPhotos: Int, // 340
        @Json(name = "total_promoted_photos")
        val totalPromotedPhotos: Int, // 119
        @Json(name = "total_illustrations")
        val totalIllustrations: Int, // 0
        @Json(name = "total_promoted_illustrations")
        val totalPromotedIllustrations: Int, // 0
        @Json(name = "accepted_tos")
        val acceptedTos: Boolean, // true
        @Json(name = "for_hire")
        val forHire: Boolean, // true
        @Json(name = "social")
        val social: Social
    ) {
        @Keep
        data class Links(
            @Json(name = "self")
            val self: String, // https://api.unsplash.com/users/jakenackos
            @Json(name = "html")
            val html: String, // https://unsplash.com/@jakenackos
            @Json(name = "photos")
            val photos: String, // https://api.unsplash.com/users/jakenackos/photos
            @Json(name = "likes")
            val likes: String, // https://api.unsplash.com/users/jakenackos/likes
            @Json(name = "portfolio")
            val portfolio: String, // https://api.unsplash.com/users/jakenackos/portfolio
            @Json(name = "following")
            val following: String, // https://api.unsplash.com/users/jakenackos/following
            @Json(name = "followers")
            val followers: String // https://api.unsplash.com/users/jakenackos/followers
        )

        @Keep
        data class ProfileImage(
            @Json(name = "small")
            val small: String, // https://images.unsplash.com/profile-1574112923531-cdc4565ead61image?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32
            @Json(name = "medium")
            val medium: String, // https://images.unsplash.com/profile-1574112923531-cdc4565ead61image?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64
            @Json(name = "large")
            val large: String // https://images.unsplash.com/profile-1574112923531-cdc4565ead61image?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128
        )

        @Keep
        data class Social(
            @Json(name = "instagram_username")
            val instagramUsername: String?, // jakenackos
            @Json(name = "portfolio_url")
            val portfolioUrl: String?, // http://jakenackos.com
            @Json(name = "twitter_username")
            val twitterUsername: String?, // JakeNackos
            @Json(name = "paypal_email")
            val paypalEmail: Any? // null
        )
    }
}
