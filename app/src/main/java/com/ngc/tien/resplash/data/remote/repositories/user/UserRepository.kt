package com.ngc.tien.resplash.data.remote.repositories.user

import com.ngc.tien.resplash.data.remote.ResplashApiService
import com.ngc.tien.resplash.data.remote.mapper.collection.Collection
import com.ngc.tien.resplash.data.remote.mapper.collection.toItem
import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.data.remote.mapper.photo.toItem
import com.ngc.tien.resplash.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

class UserRepository @Inject constructor(
    private val resplashApiService: ResplashApiService
) {
    suspend fun getPhotos(
        query: String, page: Int, perPage: Int = Constants.PAGE_PER_REQUEST,
    ): List<Photo> {
        return resplashApiService.getUserPhotos(query, page, perPage).map { it.toItem() }
    }

    suspend fun getLikePhotos(
        query: String, page: Int, perPage: Int = Constants.PAGE_PER_REQUEST,
    ): List<Photo> {
        return resplashApiService.getUserLikePhotos(query, page, perPage).map { it.toItem() }
    }

    suspend fun getCollections(
        query: String, page: Int, perPage: Int = Constants.PAGE_PER_REQUEST,
    ): List<Collection> {
        return resplashApiService.getUserCollections(query, page, perPage).map { it.toItem() }
    }
}