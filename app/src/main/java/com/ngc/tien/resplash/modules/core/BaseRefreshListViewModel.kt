package com.ngc.tien.resplash.modules.core

import androidx.lifecycle.LiveData

interface IBaseRefreshListViewModel {
    val uiStateLiveData: LiveData<BaseRefreshListUiState>

    fun loadFirstPage()

    fun loadNextPage()
}