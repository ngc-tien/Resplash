package com.ngc.tien.resplash.data.remote.repositories.photo

import com.ngc.tien.resplash.data.remote.ResplashApiService
import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.data.remote.mapper.photo.toItem
import com.ngc.tien.resplash.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val resplashApiService: ResplashApiService,
) {
    suspend fun getPhotos(
        page: Int, perPage: Int = Constants.PAGE_PER_REQUEST,
    ): Flow<List<Photo>> = flow {
        resplashApiService.getPhotos(page, perPage).map {
            it.toItem()
        }
    }

    suspend fun getPhotosById(
        id: String,
    ): Flow<Photo> = flow {
        resplashApiService.getPhotosById(id).toItem()
    }
}