package com.ngc.tien.resplash.modules.core

enum class RequestType {
    Home,
    Collection,
    Search,
    UserPhotos,
    UserLikes,
    UserCollections;

    var query: String = ""
}
