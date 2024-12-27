package com.ngc.tien.resplash.data.remote.repositories.collection

import com.ngc.tien.resplash.data.remote.ResplashApiService
import com.ngc.tien.resplash.data.remote.mapper.collection.Collection
import com.ngc.tien.resplash.data.remote.mapper.collection.toItem
import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.data.remote.mapper.photo.toItem
import com.ngc.tien.resplash.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

class CollectionRepository @Inject constructor(
    private val resplashApiService: ResplashApiService
) {
    suspend fun getCollections(
        page: Int, perPage: Int = Constants.PAGE_PER_REQUEST,
    ): List<Collection> {
        return resplashApiService.getCollections(page, perPage).map { it.toItem() }
    }

    suspend fun getCollectionPhotos(
        id: String,
        page: Int,
    ): List<Photo> {
        return resplashApiService.getCollectionPhotos(id, page, Constants.PAGE_PER_REQUEST).map { it.toItem() }
    }
}