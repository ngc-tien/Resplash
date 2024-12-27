package com.ngc.tien.resplash.modules.collections

import com.ngc.tien.resplash.data.remote.mapper.collection.Collection
import com.ngc.tien.resplash.data.remote.repositories.collection.CollectionRepository
import com.ngc.tien.resplash.data.remote.repositories.search.SearchRepository
import com.ngc.tien.resplash.data.remote.repositories.user.UserRepository
import com.ngc.tien.resplash.modules.core.BaseViewModel
import com.ngc.tien.resplash.modules.core.NetworkRequestEvent
import com.ngc.tien.resplash.modules.core.NetworkRequestEvent.Collections
import com.ngc.tien.resplash.modules.core.NetworkRequestEvent.Collections.Type.*;
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val collectionRepository: CollectionRepository,
    private val searchRepository: SearchRepository,
    private val userRepository: UserRepository
) : BaseViewModel<Collection>() {

    fun loadFirstPage(event: NetworkRequestEvent) {
        networkRequestEvent = event
        loadFirstPage()
    }

    override suspend fun getData(page: Int): List<Collection> {
        var requestType = networkRequestEvent as Collections
        return when (requestType.type) {
            Search -> searchRepository.searchCollections(requestType.queryString, page)
            GetByUser -> userRepository.getCollections(requestType.queryString, page)
            else -> collectionRepository.getCollections(page)
        }
    }
}