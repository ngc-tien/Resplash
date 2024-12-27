package com.ngc.tien.resplash.modules.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngc.tien.resplash.util.getErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel<T : BaseRefreshListItem> : ViewModel() {
    private val uiState =
        MutableLiveData<BaseRefreshListUiState>(BaseRefreshListUiState.FirstPageLoading)

    val uiStateLiveData: LiveData<BaseRefreshListUiState> get() = uiState

    lateinit var networkRequestEvent: NetworkRequestEvent

    private var firstPageLoadingComplete = false

    fun loadFirstPage() {
        if (firstPageLoadingComplete) {
            return
        }
        viewModelScope.launch {
            uiState.value = BaseRefreshListUiState.FirstPageLoading
            try {
                val items = withContext(Dispatchers.IO) {
                    getData(page = 1)
                }
                uiState.value = BaseRefreshListUiState.Content(
                    items = items,
                    currentPage = 1,
                    nextPageState = BaseRefreshListUiState.NextPageState.Idle,
                )
            } catch (ex: Exception) {
                uiState.value = BaseRefreshListUiState.FirstPageError(getErrorMessage(ex))
            }
            firstPageLoadingComplete = true
        }
    }

    fun refresh() {
        firstPageLoadingComplete = false
        loadFirstPage()
    }

    fun loadNextPage() {
        val state = uiState.value!!
        if (state !is BaseRefreshListUiState.Content) {
            return
        }

        when (state.nextPageState) {
            BaseRefreshListUiState.NextPageState.Done -> return
            BaseRefreshListUiState.NextPageState.Loading -> return
            BaseRefreshListUiState.NextPageState.Error,
            BaseRefreshListUiState.NextPageState.Idle -> {
                uiState.value =
                    state.copy(nextPageState = BaseRefreshListUiState.NextPageState.Loading)
                viewModelScope.launch {
                    val nextPage = state.currentPage + 1
                    try {
                        val newItems = withContext(Dispatchers.IO) {
                            getData(page = nextPage)
                        }
                        uiState.value = state.copy(
                            items = (state.items + newItems).distinctBy { it.id },
                            currentPage = nextPage,
                            nextPageState = if (newItems.size < 10) {
                                BaseRefreshListUiState.NextPageState.Done
                            } else {
                                BaseRefreshListUiState.NextPageState.Idle
                            }
                        )
                    } catch (e: Exception) {
                        uiState.value =
                            state.copy(nextPageState = BaseRefreshListUiState.NextPageState.Error)
                    }
                }
            }
        }
    }

    protected abstract suspend fun getData(page: Int): List<BaseRefreshListItem>
}