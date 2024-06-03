package com.ngc.tien.resplash.data.remote.repositories.collection

import com.ngc.tien.resplash.data.remote.ResplashApiService
import com.ngc.tien.resplash.data.remote.mapper.collection.Collection
import com.ngc.tien.resplash.data.remote.mapper.collection.toItem
import com.ngc.tien.resplash.util.Constants
import dagger.Component
import javax.inject.Inject

@Component
class CollectionRepository @Inject constructor(
    private val resplashApiService: ResplashApiService
) {
    suspend fun getCollections(
        page: Int, perPage: Int = Constants.PAGE_PER_REQUEST,
    ): List<Collection> {
        return resplashApiService.getCollections(page, perPage).map { it.toItem() }
    }
}