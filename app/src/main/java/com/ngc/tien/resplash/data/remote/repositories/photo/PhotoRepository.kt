package com.ngc.tien.resplash.data.remote.repositories.photo

import com.ngc.tien.resplash.data.remote.ResplashApiService
import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.data.remote.mapper.photo.toItem
import com.ngc.tien.resplash.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(
    private val resplashApiService: ResplashApiService
) {
    suspend fun getPhotos(
        page: Int, perPage: Int = Constants.PAGE_PER_REQUEST,
    ): List<Photo> {
        return resplashApiService.getPhotos(page, perPage).map {
            it.toItem()
        }
    }

    suspend fun getPhotosById(
        id: String,
    ): Photo {
        return resplashApiService.getPhotosById(id).toItem()
    }
}