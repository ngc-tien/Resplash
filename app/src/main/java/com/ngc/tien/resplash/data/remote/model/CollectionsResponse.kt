package com.ngc.tien.resplash.data.remote.model


import com.squareup.moshi.Json
import androidx.annotation.Keep

@Keep
data class CollectionsResponseItem(
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
    val user: User,
    @Json(name = "cover_photo")
    val coverPhoto: CoverPhoto,
    @Json(name = "preview_photos")
    val previewPhotos: List<PreviewPhoto>
) {
    @Keep
    data class Tag(
        @Json(name = "type")
        val type: String, // search
        @Json(name = "title")
        val title: String, // american city
        @Json(name = "source")
        val source: Source?
    ) {
        @Keep
        data class Source(
            @Json(name = "ancestry")
            val ancestry: Ancestry,
            @Json(name = "title")
            val title: String, // America images & photos
            @Json(name = "subtitle")
            val subtitle: String, // Download free america photos & images
            @Json(name = "description")
            val description: String, // Choose from a curated selection of America photos. Always free on Unsplash.
            @Json(name = "meta_title")
            val metaTitle: String, // 500+ Stunning America Pictures [HD] | Download Free Images & Stock Photos on Unsplash
            @Json(name = "meta_description")
            val metaDescription: String, // Choose from hundreds of free America pictures. Download HD America photos for free on Unsplash.
            @Json(name = "cover_photo")
            val coverPhoto: CoverPhoto
        ) {
            @Keep
            data class Ancestry(
                @Json(name = "type")
                val type: Type,
                @Json(name = "category")
                val category: Category?,
                @Json(name = "subcategory")
                val subcategory: Subcategory?
            ) {
                @Keep
                data class Type(
                    @Json(name = "slug")
                    val slug: String, // images
                    @Json(name = "pretty_slug")
                    val prettySlug: String // Images
                )

                @Keep
                data class Category(
                    @Json(name = "slug")
                    val slug: String, // travel
                    @Json(name = "pretty_slug")
                    val prettySlug: String // Travel
                )

                @Keep
                data class Subcategory(
                    @Json(name = "slug")
                    val slug: String, // america
                    @Json(name = "pretty_slug")
                    val prettySlug: String // America
                )
            }

            @Keep
            data class CoverPhoto(
                @Json(name = "id")
                val id: String, // JB92NeJSxW4
                @Json(name = "slug")
                val slug: String, // man-riding-bike-and-woman-running-holding-flag-of-usa-JB92NeJSxW4
                @Json(name = "alternative_slugs")
                val alternativeSlugs: AlternativeSlugs,
                @Json(name = "created_at")
                val createdAt: String, // 2016-09-05T15:54:04Z
                @Json(name = "updated_at")
                val updatedAt: String, // 2024-05-20T14:18:31Z
                @Json(name = "promoted_at")
                val promotedAt: String?, // 2016-09-05T15:54:04Z
                @Json(name = "width")
                val width: Int, // 3000
                @Json(name = "height")
                val height: Int, // 2400
                @Json(name = "color")
                val color: String, // #59a6d9
                @Json(name = "blur_hash")
                val blurHash: String?, // LmGJjTtPWCofG1ovagbHIWW;ofay
                @Json(name = "description")
                val description: String?, // People with US flags
                @Json(name = "alt_description")
                val altDescription: String?, // man riding bike and woman running holding flag of USA
                @Json(name = "breadcrumbs")
                val breadcrumbs: List<Breadcrumb>,
                @Json(name = "urls")
                val urls: Urls,
                @Json(name = "links")
                val links: Links,
                @Json(name = "likes")
                val likes: Int, // 816
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
                @Json(name = "premium")
                val premium: Boolean?, // false
                @Json(name = "plus")
                val plus: Boolean?, // false
                @Json(name = "user")
                val user: User
            ) {
                @Keep
                data class AlternativeSlugs(
                    @Json(name = "en")
                    val en: String, // man-riding-bike-and-woman-running-holding-flag-of-usa-JB92NeJSxW4
                    @Json(name = "es")
                    val es: String, // hombre-montando-en-bicicleta-y-mujer-corriendo-sosteniendo-la-bandera-de-ee-uu-JB92NeJSxW4
                    @Json(name = "ja")
                    val ja: String, // 自転車に乗った男性とアメリカの旗を持って走る女性-JB92NeJSxW4
                    @Json(name = "fr")
                    val fr: String, // homme-faisant-du-velo-et-femme-courant-tenant-le-drapeau-des-etats-unis-JB92NeJSxW4
                    @Json(name = "it")
                    val `it`: String, // uomo-che-guida-la-bici-e-la-donna-che-corre-tenendo-la-bandiera-degli-stati-uniti-JB92NeJSxW4
                    @Json(name = "ko")
                    val ko: String, // 자전거를-타고-있는-남자와-미국의-국기를-들고-달리는-여자-JB92NeJSxW4
                    @Json(name = "de")
                    val de: String, // mann-fahrt-fahrrad-und-frau-lauft-mit-flagge-der-usa-JB92NeJSxW4
                    @Json(name = "pt")
                    val pt: String // homem-que-anda-de-bicicleta-e-mulher-que-corre-segurando-a-bandeira-dos-eua-JB92NeJSxW4
                )

                @Keep
                data class Breadcrumb(
                    @Json(name = "slug")
                    val slug: String, // images
                    @Json(name = "title")
                    val title: String, // 1,000,000+ Free Images
                    @Json(name = "index")
                    val index: Int, // 0
                    @Json(name = "type")
                    val type: String // landing_page
                )

                @Keep
                data class Urls(
                    @Json(name = "raw")
                    val raw: String, // https://images.unsplash.com/photo-1473090826765-d54ac2fdc1eb?ixlib=rb-4.0.3
                    @Json(name = "full")
                    val full: String, // https://images.unsplash.com/photo-1473090826765-d54ac2fdc1eb?ixlib=rb-4.0.3&q=85&fm=jpg&crop=entropy&cs=srgb
                    @Json(name = "regular")
                    val regular: String, // https://images.unsplash.com/photo-1473090826765-d54ac2fdc1eb?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max
                    @Json(name = "small")
                    val small: String, // https://images.unsplash.com/photo-1473090826765-d54ac2fdc1eb?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max
                    @Json(name = "thumb")
                    val thumb: String, // https://images.unsplash.com/photo-1473090826765-d54ac2fdc1eb?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max
                    @Json(name = "small_s3")
                    val smallS3: String // https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1473090826765-d54ac2fdc1eb
                )

                @Keep
                data class Links(
                    @Json(name = "self")
                    val self: String, // https://api.unsplash.com/photos/man-riding-bike-and-woman-running-holding-flag-of-usa-JB92NeJSxW4
                    @Json(name = "html")
                    val html: String, // https://unsplash.com/photos/man-riding-bike-and-woman-running-holding-flag-of-usa-JB92NeJSxW4
                    @Json(name = "download")
                    val download: String, // https://unsplash.com/photos/JB92NeJSxW4/download
                    @Json(name = "download_location")
                    val downloadLocation: String // https://api.unsplash.com/photos/JB92NeJSxW4/download
                )

                @Keep
                data class TopicSubmissions(
                    @Json(name = "health")
                    val health: Health?,
                    @Json(name = "people")
                    val people: People?,
                    @Json(name = "textures-patterns")
                    val texturesPatterns: TexturesPatterns?,
                    @Json(name = "wallpapers")
                    val wallpapers: Wallpapers?,
                    @Json(name = "nature")
                    val nature: Nature?,
                    @Json(name = "architecture-interior")
                    val architectureInterior: ArchitectureInterior?,
                    @Json(name = "color-of-water")
                    val colorOfWater: ColorOfWater?,
                    @Json(name = "animals")
                    val animals: Animals?
                ) {
                    @Keep
                    data class Health(
                        @Json(name = "status")
                        val status: String, // approved
                        @Json(name = "approved_on")
                        val approvedOn: String? // 2020-05-01T12:21:54Z
                    )

                    @Keep
                    data class People(
                        @Json(name = "status")
                        val status: String, // approved
                        @Json(name = "approved_on")
                        val approvedOn: String? // 2020-04-06T14:20:24Z
                    )

                    @Keep
                    data class TexturesPatterns(
                        @Json(name = "status")
                        val status: String, // approved
                        @Json(name = "approved_on")
                        val approvedOn: String? // 2020-04-06T14:20:11Z
                    )

                    @Keep
                    data class Wallpapers(
                        @Json(name = "status")
                        val status: String, // approved
                        @Json(name = "approved_on")
                        val approvedOn: String? // 2020-04-06T14:20:09Z
                    )

                    @Keep
                    data class Nature(
                        @Json(name = "status")
                        val status: String, // approved
                        @Json(name = "approved_on")
                        val approvedOn: String? // 2020-04-06T14:20:12Z
                    )

                    @Keep
                    data class ArchitectureInterior(
                        @Json(name = "status")
                        val status: String, // approved
                        @Json(name = "approved_on")
                        val approvedOn: String? // 2020-04-06T14:20:14Z
                    )

                    @Keep
                    data class ColorOfWater(
                        @Json(name = "status")
                        val status: String, // approved
                        @Json(name = "approved_on")
                        val approvedOn: String? // 2022-04-21T15:04:21Z
                    )

                    @Keep
                    data class Animals(
                        @Json(name = "status")
                        val status: String, // approved
                        @Json(name = "approved_on")
                        val approvedOn: String? // 2021-06-09T15:10:40Z
                    )
                }

                @Keep
                data class User(
                    @Json(name = "id")
                    val id: String, // psm5wZXulHQ
                    @Json(name = "updated_at")
                    val updatedAt: String, // 2024-05-16T08:57:56Z
                    @Json(name = "username")
                    val username: String, // frankiefoto
                    @Json(name = "name")
                    val name: String, // frank mckenna
                    @Json(name = "first_name")
                    val firstName: String, // frank
                    @Json(name = "last_name")
                    val lastName: String?, // mckenna
                    @Json(name = "twitter_username")
                    val twitterUsername: String?, // filipmroz
                    @Json(name = "portfolio_url")
                    val portfolioUrl: String?, // http://blog.frankiefoto.com
                    @Json(name = "bio")
                    val bio: String?, // Take a walk in San Diego. Find Something Beautiful in San Diego.
                    @Json(name = "location")
                    val location: String?, // San Diego
                    @Json(name = "links")
                    val links: Links,
                    @Json(name = "profile_image")
                    val profileImage: ProfileImage,
                    @Json(name = "instagram_username")
                    val instagramUsername: String?, // frankiefoto
                    @Json(name = "total_collections")
                    val totalCollections: Int, // 1
                    @Json(name = "total_likes")
                    val totalLikes: Int, // 20
                    @Json(name = "total_photos")
                    val totalPhotos: Int, // 167
                    @Json(name = "total_promoted_photos")
                    val totalPromotedPhotos: Int, // 71
                    @Json(name = "total_illustrations")
                    val totalIllustrations: Int, // 0
                    @Json(name = "total_promoted_illustrations")
                    val totalPromotedIllustrations: Int, // 0
                    @Json(name = "accepted_tos")
                    val acceptedTos: Boolean, // false
                    @Json(name = "for_hire")
                    val forHire: Boolean, // false
                    @Json(name = "social")
                    val social: Social
                ) {
                    @Keep
                    data class Links(
                        @Json(name = "self")
                        val self: String, // https://api.unsplash.com/users/frankiefoto
                        @Json(name = "html")
                        val html: String, // https://unsplash.com/@frankiefoto
                        @Json(name = "photos")
                        val photos: String, // https://api.unsplash.com/users/frankiefoto/photos
                        @Json(name = "likes")
                        val likes: String, // https://api.unsplash.com/users/frankiefoto/likes
                        @Json(name = "portfolio")
                        val portfolio: String, // https://api.unsplash.com/users/frankiefoto/portfolio
                        @Json(name = "following")
                        val following: String, // https://api.unsplash.com/users/frankiefoto/following
                        @Json(name = "followers")
                        val followers: String // https://api.unsplash.com/users/frankiefoto/followers
                    )

                    @Keep
                    data class ProfileImage(
                        @Json(name = "small")
                        val small: String, // https://images.unsplash.com/profile-1470093657121-e62c91ef54e3?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32
                        @Json(name = "medium")
                        val medium: String, // https://images.unsplash.com/profile-1470093657121-e62c91ef54e3?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64
                        @Json(name = "large")
                        val large: String // https://images.unsplash.com/profile-1470093657121-e62c91ef54e3?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128
                    )

                    @Keep
                    data class Social(
                        @Json(name = "instagram_username")
                        val instagramUsername: String?, // frankiefoto
                        @Json(name = "portfolio_url")
                        val portfolioUrl: String?, // http://blog.frankiefoto.com
                        @Json(name = "twitter_username")
                        val twitterUsername: String?, // filipmroz
                        @Json(name = "paypal_email")
                        val paypalEmail: Any? // null
                    )
                }
            }
        }
    }

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

    @Keep
    data class User(
        @Json(name = "id")
        val id: String, // iwi9-4OXLYY
        @Json(name = "updated_at")
        val updatedAt: String, // 2024-05-22T05:13:25Z
        @Json(name = "username")
        val username: String, // unsplashplus
        @Json(name = "name")
        val name: String, // Unsplash+ Collections
        @Json(name = "first_name")
        val firstName: String, // Unsplash+
        @Json(name = "last_name")
        val lastName: String?, // Collections
        @Json(name = "twitter_username")
        val twitterUsername: String?, // andrewneel
        @Json(name = "portfolio_url")
        val portfolioUrl: String?, // https://andrewtneel.com
        @Json(name = "bio")
        val bio: String?, // Creator + Head of Content at Pexels  |  Support me via the "PayPal" button ☝️  |  Donation Goal: $2 of $300 — Thank you Kathy R. 🖤
        @Json(name = "location")
        val location: String?, // North Carolina
        @Json(name = "links")
        val links: Links,
        @Json(name = "profile_image")
        val profileImage: ProfileImage,
        @Json(name = "instagram_username")
        val instagramUsername: String?, // andrewtneel
        @Json(name = "total_collections")
        val totalCollections: Int, // 166
        @Json(name = "total_likes")
        val totalLikes: Int, // 223
        @Json(name = "total_photos")
        val totalPhotos: Int, // 0
        @Json(name = "total_promoted_photos")
        val totalPromotedPhotos: Int, // 0
        @Json(name = "total_illustrations")
        val totalIllustrations: Int, // 0
        @Json(name = "total_promoted_illustrations")
        val totalPromotedIllustrations: Int, // 0
        @Json(name = "accepted_tos")
        val acceptedTos: Boolean, // true
        @Json(name = "for_hire")
        val forHire: Boolean, // false
        @Json(name = "social")
        val social: Social
    ) {
        @Keep
        data class Links(
            @Json(name = "self")
            val self: String, // https://api.unsplash.com/users/unsplashplus
            @Json(name = "html")
            val html: String, // https://unsplash.com/@unsplashplus
            @Json(name = "photos")
            val photos: String, // https://api.unsplash.com/users/unsplashplus/photos
            @Json(name = "likes")
            val likes: String, // https://api.unsplash.com/users/unsplashplus/likes
            @Json(name = "portfolio")
            val portfolio: String, // https://api.unsplash.com/users/unsplashplus/portfolio
            @Json(name = "following")
            val following: String, // https://api.unsplash.com/users/unsplashplus/following
            @Json(name = "followers")
            val followers: String // https://api.unsplash.com/users/unsplashplus/followers
        )

        @Keep
        data class ProfileImage(
            @Json(name = "small")
            val small: String, // https://images.unsplash.com/profile-1714421769490-6918cb0c83a9image?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32
            @Json(name = "medium")
            val medium: String, // https://images.unsplash.com/profile-1714421769490-6918cb0c83a9image?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64
            @Json(name = "large")
            val large: String // https://images.unsplash.com/profile-1714421769490-6918cb0c83a9image?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128
        )

        @Keep
        data class Social(
            @Json(name = "instagram_username")
            val instagramUsername: String?, // andrewtneel
            @Json(name = "portfolio_url")
            val portfolioUrl: String?, // https://andrewtneel.com
            @Json(name = "twitter_username")
            val twitterUsername: String?, // andrewneel
            @Json(name = "paypal_email")
            val paypalEmail: Any? // null
        )
    }

    @Keep
    data class CoverPhoto(
        @Json(name = "id")
        val id: String, // v_-HplESEyc
        @Json(name = "slug")
        val slug: String, // a-view-of-the-golden-gate-bridge-in-san-francisco-v_-HplESEyc
        @Json(name = "alternative_slugs")
        val alternativeSlugs: AlternativeSlugs,
        @Json(name = "created_at")
        val createdAt: String, // 2022-12-12T12:42:58Z
        @Json(name = "updated_at")
        val updatedAt: String, // 2024-05-22T15:02:05Z
        @Json(name = "promoted_at")
        val promotedAt: String?, // 2024-04-11T08:45:06Z
        @Json(name = "width")
        val width: Int, // 5793
        @Json(name = "height")
        val height: Int, // 3862
        @Json(name = "color")
        val color: String, // #EFEFEF
        @Json(name = "blur_hash")
        val blurHash: String?, // LgLX9}^foz%1yGR*RkWX9boIodax
        @Json(name = "description")
        val description: String?, // Iceland
        @Json(name = "alt_description")
        val altDescription: String?, // a view of the golden gate bridge in san francisco
        @Json(name = "breadcrumbs")
        val breadcrumbs: List<Breadcrumb>,
        @Json(name = "urls")
        val urls: Urls,
        @Json(name = "links")
        val links: Links,
        @Json(name = "likes")
        val likes: Int, // 5
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
    ) {
        @Keep
        data class AlternativeSlugs(
            @Json(name = "en")
            val en: String, // a-view-of-the-golden-gate-bridge-in-san-francisco-v_-HplESEyc
            @Json(name = "es")
            val es: String, // una-vista-del-puente-golden-gate-en-san-francisco-v_-HplESEyc
            @Json(name = "ja")
            val ja: String, // サンフランシスコのゴールデンゲートブリッジの眺め-v_-HplESEyc
            @Json(name = "fr")
            val fr: String, // une-vue-du-golden-gate-bridge-a-san-francisco-v_-HplESEyc
            @Json(name = "it")
            val `it`: String, // una-vista-del-golden-gate-bridge-a-san-francisco-v_-HplESEyc
            @Json(name = "ko")
            val ko: String, // 샌프란시스코의-금문교-전경-v_-HplESEyc
            @Json(name = "de")
            val de: String, // blick-auf-die-golden-gate-bridge-in-san-francisco-v_-HplESEyc
            @Json(name = "pt")
            val pt: String // uma-vista-da-ponte-do-portao-dourado-em-san-francisco-v_-HplESEyc
        )

        @Keep
        data class Breadcrumb(
            @Json(name = "slug")
            val slug: String, // images
            @Json(name = "title")
            val title: String, // 1,000,000+ Free Images
            @Json(name = "index")
            val index: Int, // 0
            @Json(name = "type")
            val type: String // landing_page
        )

        @Keep
        data class Urls(
            @Json(name = "raw")
            val raw: String, // https://plus.unsplash.com/premium_photo-1670603709901-c867937fe557?ixlib=rb-4.0.3
            @Json(name = "full")
            val full: String, // https://plus.unsplash.com/premium_photo-1670603709901-c867937fe557?ixlib=rb-4.0.3&q=85&fm=jpg&crop=entropy&cs=srgb
            @Json(name = "regular")
            val regular: String, // https://plus.unsplash.com/premium_photo-1670603709901-c867937fe557?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max
            @Json(name = "small")
            val small: String, // https://plus.unsplash.com/premium_photo-1670603709901-c867937fe557?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max
            @Json(name = "thumb")
            val thumb: String, // https://plus.unsplash.com/premium_photo-1670603709901-c867937fe557?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max
            @Json(name = "small_s3")
            val smallS3: String // https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/unsplash-premium-photos-production/premium_photo-1670603709901-c867937fe557
        )

        @Keep
        data class Links(
            @Json(name = "self")
            val self: String, // https://api.unsplash.com/photos/a-view-of-the-golden-gate-bridge-in-san-francisco-v_-HplESEyc
            @Json(name = "html")
            val html: String, // https://unsplash.com/photos/a-view-of-the-golden-gate-bridge-in-san-francisco-v_-HplESEyc
            @Json(name = "download")
            val download: String, // https://unsplash.com/photos/v_-HplESEyc/download
            @Json(name = "download_location")
            val downloadLocation: String // https://api.unsplash.com/photos/v_-HplESEyc/download
        )

        @Keep
        data class TopicSubmissions(
            @Json(name = "business-work")
            val businessWork: BusinessWork?,
            @Json(name = "experimental")
            val experimental: Experimental?,
            @Json(name = "fashion-beauty")
            val fashionBeauty: FashionBeauty?,
            @Json(name = "nature")
            val nature: Nature?,
            @Json(name = "entrepreneur")
            val entrepreneur: Entrepreneur?
        ) {
            @Keep
            data class BusinessWork(
                @Json(name = "status")
                val status: String, // approved
                @Json(name = "approved_on")
                val approvedOn: String? // 2024-03-12T22:26:48Z
            )

            @Keep
            data class Experimental(
                @Json(name = "status")
                val status: String, // approved
                @Json(name = "approved_on")
                val approvedOn: String? // 2024-03-12T22:26:42Z
            )

            @Keep
            data class FashionBeauty(
                @Json(name = "status")
                val status: String, // approved
                @Json(name = "approved_on")
                val approvedOn: String? // 2024-03-28T14:13:00Z
            )

            @Keep
            data class Nature(
                @Json(name = "status")
                val status: String, // approved
                @Json(name = "approved_on")
                val approvedOn: String? // 2023-12-13T14:52:24Z
            )

            @Keep
            data class Entrepreneur(
                @Json(name = "status")
                val status: String, // approved
                @Json(name = "approved_on")
                val approvedOn: String? // 2022-03-15T11:44:07Z
            )
        }

        @Keep
        data class User(
            @Json(name = "id")
            val id: String, // LHntbSqbucM
            @Json(name = "updated_at")
            val updatedAt: String, // 2024-05-08T01:22:15Z
            @Json(name = "username")
            val username: String, // paulius005
            @Json(name = "name")
            val name: String, // Paulius Dragunas
            @Json(name = "first_name")
            val firstName: String, // Paulius
            @Json(name = "last_name")
            val lastName: String?, // Dragunas
            @Json(name = "twitter_username")
            val twitterUsername: String?, // _pauliusd
            @Json(name = "portfolio_url")
            val portfolioUrl: String?, // http://pauliusdragunas.com
            @Json(name = "bio")
            val bio: String?, // I'm a Brazilian woman who loves to hear people’s thoughts, searching for projects that deeply impact my soul. Based in Rio, available to travel.
            @Json(name = "location")
            val location: String?, // Brasil
            @Json(name = "links")
            val links: Links,
            @Json(name = "profile_image")
            val profileImage: ProfileImage,
            @Json(name = "instagram_username")
            val instagramUsername: String?, // nataliablauth
            @Json(name = "total_collections")
            val totalCollections: Int, // 3
            @Json(name = "total_likes")
            val totalLikes: Int, // 9
            @Json(name = "total_photos")
            val totalPhotos: Int, // 83
            @Json(name = "total_promoted_photos")
            val totalPromotedPhotos: Int, // 21
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
                val self: String, // https://api.unsplash.com/users/paulius005
                @Json(name = "html")
                val html: String, // https://unsplash.com/@paulius005
                @Json(name = "photos")
                val photos: String, // https://api.unsplash.com/users/paulius005/photos
                @Json(name = "likes")
                val likes: String, // https://api.unsplash.com/users/paulius005/likes
                @Json(name = "portfolio")
                val portfolio: String, // https://api.unsplash.com/users/paulius005/portfolio
                @Json(name = "following")
                val following: String, // https://api.unsplash.com/users/paulius005/following
                @Json(name = "followers")
                val followers: String // https://api.unsplash.com/users/paulius005/followers
            )

            @Keep
            data class ProfileImage(
                @Json(name = "small")
                val small: String, // https://images.unsplash.com/profile-1521858929575-a520e0b8771d?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32
                @Json(name = "medium")
                val medium: String, // https://images.unsplash.com/profile-1521858929575-a520e0b8771d?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64
                @Json(name = "large")
                val large: String // https://images.unsplash.com/profile-1521858929575-a520e0b8771d?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128
            )

            @Keep
            data class Social(
                @Json(name = "instagram_username")
                val instagramUsername: String?, // nataliablauth
                @Json(name = "portfolio_url")
                val portfolioUrl: String?, // http://pauliusdragunas.com
                @Json(name = "twitter_username")
                val twitterUsername: String?, // _pauliusd
                @Json(name = "paypal_email")
                val paypalEmail: Any? // null
            )
        }
    }

    @Keep
    data class PreviewPhoto(
        @Json(name = "id")
        val id: String, // v_-HplESEyc
        @Json(name = "slug")
        val slug: String, // a-view-of-the-golden-gate-bridge-in-san-francisco-v_-HplESEyc
        @Json(name = "created_at")
        val createdAt: String, // 2022-12-12T12:42:58Z
        @Json(name = "updated_at")
        val updatedAt: String, // 2024-05-22T15:02:05Z
        @Json(name = "blur_hash")
        val blurHash: String?, // LgLX9}^foz%1yGR*RkWX9boIodax
        @Json(name = "asset_type")
        val assetType: String, // photo
        @Json(name = "urls")
        val urls: Urls
    ) {
        @Keep
        data class Urls(
            @Json(name = "raw")
            val raw: String, // https://plus.unsplash.com/premium_photo-1670603709901-c867937fe557?ixlib=rb-4.0.3
            @Json(name = "full")
            val full: String, // https://plus.unsplash.com/premium_photo-1670603709901-c867937fe557?ixlib=rb-4.0.3&q=85&fm=jpg&crop=entropy&cs=srgb
            @Json(name = "regular")
            val regular: String, // https://plus.unsplash.com/premium_photo-1670603709901-c867937fe557?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max
            @Json(name = "small")
            val small: String, // https://plus.unsplash.com/premium_photo-1670603709901-c867937fe557?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max
            @Json(name = "thumb")
            val thumb: String, // https://plus.unsplash.com/premium_photo-1670603709901-c867937fe557?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max
            @Json(name = "small_s3")
            val smallS3: String // https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/unsplash-premium-photos-production/premium_photo-1670603709901-c867937fe557
        )
    }
}
