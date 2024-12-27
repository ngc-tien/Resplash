package com.ngc.tien.resplash.data.remote.model.collection

import androidx.annotation.Keep
import com.squareup.moshi.Json

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