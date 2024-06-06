package com.ngc.tien.resplash.data.remote

import com.ngc.tien.resplash.data.remote.model.collection.CollectionsResponse
import com.ngc.tien.resplash.data.remote.model.photo.PhotoResponse
import com.ngc.tien.resplash.data.remote.model.search.SearchCollectionsResponse
import com.ngc.tien.resplash.data.remote.model.search.SearchPhotosResponse
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
        @Query("per_page") perPage: Int,
    ): List<PhotoResponse>

    @GET("photos/{id}")
    suspend fun getPhotosById(
        @Path("id") id: String,
    ): PhotoResponse

    @GET("collections")
    suspend fun getCollections(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<CollectionsResponse>

    @GET("collections/{id}")
    suspend fun getCollectionById(
        @Path("id") id: String,
    ): CollectionsResponse

    @GET("collections/{id}/photos")
    suspend fun getCollectionPhotos(
        @Path("id") id: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<PhotoResponse>

    @GET("/search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): SearchPhotosResponse

    @GET("/search/collections")
    suspend fun searchCollections(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): SearchCollectionsResponse
}