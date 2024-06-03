package com.ngc.tien.resplash.modules.core

import androidx.lifecycle.LiveData
import com.ngc.tien.resplash.data.remote.mapper.collection.Collection
import com.ngc.tien.resplash.util.UiState

interface IBaseRefreshListViewModel {
    val uiStateLiveData: LiveData<UiState<List<Collection>>>

    fun loadFirstPage()

    fun loadNextPage()
}