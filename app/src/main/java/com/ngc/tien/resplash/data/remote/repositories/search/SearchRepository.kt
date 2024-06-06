package com.ngc.tien.resplash.data.remote.repositories.search

import com.ngc.tien.resplash.data.remote.ResplashApiService
import com.ngc.tien.resplash.data.remote.mapper.collection.Collection
import com.ngc.tien.resplash.data.remote.mapper.collection.toItem
import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.data.remote.mapper.photo.toItem
import com.ngc.tien.resplash.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val resplashApiService: ResplashApiService
) {
    suspend fun searchPhotos(
        query: String, page: Int, perPage: Int = Constants.PAGE_PER_REQUEST,
    ): List<Photo> {
        return resplashApiService.searchPhotos(query, page, perPage).results.map { it.toItem() }
    }

    suspend fun searchCollections(
        query: String, page: Int, perPage: Int = Constants.PAGE_PER_REQUEST,
    ): List<Collection> {
        return resplashApiService.searchCollections(query, page, perPage).results.map { it.toItem() }
    }
}