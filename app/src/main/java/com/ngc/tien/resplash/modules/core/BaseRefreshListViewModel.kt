package com.ngc.tien.resplash.modules.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface IBaseRefreshListViewModel {
    val uiStateLiveData: LiveData<BaseRefreshListUiState>

    fun loadFirstPage()

    fun loadNextPage()
}