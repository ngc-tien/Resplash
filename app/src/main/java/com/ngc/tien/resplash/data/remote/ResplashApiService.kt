package com.ngc.tien.resplash.data.remote

import com.ngc.tien.resplash.data.remote.model.CollectionsResponseItem
import com.ngc.tien.resplash.data.remote.model.PhotoDetailResponse
import com.ngc.tien.resplash.data.remote.model.PhotoResponseItem
import com.ngc.tien.resplash.util.Constants.PAGE_PER_REQUEST
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ResplashApiService {
    companion object {
        operator fun invoke(retrofit: Retrofit): ResplashApiService = retrofit.create()
    }

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PAGE_PER_REQUEST,
    ): List<PhotoResponseItem>

    @GET("photos/{id}")
    suspend fun getPhotosById(
        @Path("id") id: String,
    ): PhotoDetailResponse

    @GET("collections")
    suspend fun getCollections(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PAGE_PER_REQUEST,
    ): List<CollectionsResponseItem>
}