package com.ngc.tien.resplash.modules.photo

import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.modules.core.BaseViewModel
import com.ngc.tien.resplash.modules.core.RequestType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor() : BaseViewModel<Photo>() {
    var selectedItemIndex = -1

    override suspend fun getData(page: Int): List<Photo> {
        return when (requestType) {
            RequestType.Collection -> collectionRepository.getCollectionPhotos(
                requestType.query,
                page
            )

            RequestType.Search -> searchRepository.searchPhotos(requestType.query, page)
            RequestType.UserPhotos -> userRepository.getPhotos(requestType.query, page)
            RequestType.UserLikes -> userRepository.getLikePhotos(requestType.query, page)
            else -> photoRepository.getPhotos(page)
        }
    }
}
