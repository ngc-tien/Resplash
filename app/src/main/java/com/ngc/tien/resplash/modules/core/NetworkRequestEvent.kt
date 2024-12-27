package com.ngc.tien.resplash.modules.core

sealed interface NetworkRequestEvent {
    data class Collections(
        val queryString: String = "",
        val type: Type = Type.Default,
    ) : NetworkRequestEvent {
        enum class Type { Default, Search, GetByUser }
    }

    data class Photo(
        val queryString: String = "",
        val type: Type = Type.Default,
    ) : NetworkRequestEvent {
        enum class Type {
            Default, Collections, UserPhotos, UserLikes, Search
        }
    }
}
