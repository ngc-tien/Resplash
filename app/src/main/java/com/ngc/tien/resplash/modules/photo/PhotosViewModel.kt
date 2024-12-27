package com.ngc.tien.resplash.modules.photo

import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.data.remote.repositories.collection.CollectionRepository
import com.ngc.tien.resplash.data.remote.repositories.photo.PhotoRepository
import com.ngc.tien.resplash.data.remote.repositories.search.SearchRepository
import com.ngc.tien.resplash.data.remote.repositories.user.UserRepository
import com.ngc.tien.resplash.modules.core.BaseViewModel
import com.ngc.tien.resplash.modules.core.NetworkRequestEvent
import com.ngc.tien.resplash.modules.core.NetworkRequestEvent.Photo.Type.*;
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val collectionRepository: CollectionRepository,
    private val searchRepository: SearchRepository,
    private val userRepository: UserRepository
) : BaseViewModel<Photo>() {
    var selectedItemIndex = -1

    fun loadFirstPage(event: NetworkRequestEvent) {
        networkRequestEvent = event
        loadFirstPage()
    }

    override suspend fun getData(page: Int): List<Photo> {
        val networkRequestEvent = networkRequestEvent as NetworkRequestEvent.Photo
        return when (networkRequestEvent.type) {
            Collections -> collectionRepository.getCollectionPhotos(networkRequestEvent.queryString, page)
            Search -> searchRepository.searchPhotos(networkRequestEvent.queryString, page)
            UserPhotos -> userRepository.getPhotos(networkRequestEvent.queryString, page)
            UserLikes -> userRepository.getLikePhotos(networkRequestEvent.queryString, page)
            else -> photoRepository.getPhotos(page)
        }
    }
}
