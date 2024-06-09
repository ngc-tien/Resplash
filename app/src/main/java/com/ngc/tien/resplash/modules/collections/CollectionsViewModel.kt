package com.ngc.tien.resplash.modules.collections

import com.ngc.tien.resplash.data.remote.mapper.collection.Collection
import com.ngc.tien.resplash.modules.core.BaseViewModel
import com.ngc.tien.resplash.modules.core.RequestType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor() : BaseViewModel<Collection>() {
    override suspend fun getData(page: Int): List<Collection> {
        return when (requestType) {
            RequestType.Search -> searchRepository.searchCollections(requestType.query, page)
            RequestType.UserCollections -> userRepository.getCollections(requestType.query, page)
            else -> collectionRepository.getCollections(page)
        }
    }
}