package com.ngc.tien.resplash.modules.collections

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngc.tien.resplash.data.remote.mapper.collection.Collection
import com.ngc.tien.resplash.data.remote.repositories.collection.CollectionRepository
import com.ngc.tien.resplash.data.remote.repositories.search.SearchRepository
import com.ngc.tien.resplash.modules.core.BaseRefreshListUiState
import com.ngc.tien.resplash.modules.core.BaseRefreshListUiState.NextPageState
import com.ngc.tien.resplash.modules.core.IBaseRefreshListViewModel
import com.ngc.tien.resplash.modules.core.RequestType
import com.ngc.tien.resplash.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val collectionRepository: CollectionRepository,
    private val searchRepository: SearchRepository,
) : ViewModel(), IBaseRefreshListViewModel {
    private val uiState =
        MutableLiveData<BaseRefreshListUiState>(BaseRefreshListUiState.FirstPageLoading)
    override val uiStateLiveData: LiveData<BaseRefreshListUiState> get() = uiState
    lateinit var requestType: RequestType

    override fun loadFirstPage() {
        viewModelScope.launch {
            uiState.value = BaseRefreshListUiState.FirstPageLoading
            try {
                val items = getCollections(page = 1)
                uiState.value = BaseRefreshListUiState.Content(
                    items = items,
                    currentPage = 1,
                    nextPageState = NextPageState.Idle,
                )
            } catch (ex: Exception) {
                uiState.value = BaseRefreshListUiState.FirstPageError(getErrorMessage(context, ex))
            }
        }
    }

    override fun loadNextPage() {
        val state = uiState.value!!
        if (state !is BaseRefreshListUiState.Content) {
            return
        }

        when (state.nextPageState) {
            NextPageState.Done -> return
            NextPageState.Loading -> return
            NextPageState.Error,
            NextPageState.Idle -> {
                uiState.value = state.copy(nextPageState = NextPageState.Loading)
                viewModelScope.launch {
                    val nextPage = state.currentPage + 1
                    try {
                        val newItems = getCollections(page = nextPage)
                        uiState.value = state.copy(
                            items = (state.items + newItems).distinctBy { it.id },
                            currentPage = nextPage,
                            nextPageState = if (newItems.size < 10) {
                                NextPageState.Done
                            } else {
                                NextPageState.Idle
                            }
                        )
                    } catch (e: Exception) {
                        uiState.value = state.copy(nextPageState = NextPageState.Error)
                    }
                }
            }
        }
    }

    private suspend fun getCollections(page: Int): List<Collection> {
        return when (requestType) {
            RequestType.Search -> searchRepository.searchCollections(requestType.query, page)
            else -> collectionRepository.getCollections(page)
        }
    }
}
