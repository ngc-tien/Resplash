package com.ngc.tien.resplash.modules.collections

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngc.tien.resplash.data.remote.repositories.collection.CollectionRepository
import com.ngc.tien.resplash.modules.core.IBaseRefreshListViewModel
import com.ngc.tien.resplash.data.remote.mapper.collection.Collection
import com.ngc.tien.resplash.util.UiState
import com.ngc.tien.resplash.util.UiState.NextPageState
import com.ngc.tien.resplash.util.safeApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val collectionRepository: CollectionRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel(), IBaseRefreshListViewModel {
    private val uiState =
        MutableLiveData<UiState<List<Collection>>>(UiState.FirstPageLoading)
    override val uiStateLiveData: LiveData<UiState<List<Collection>>> get() = uiState

    override fun loadFirstPage() {
        viewModelScope.launch {
            uiState.value = UiState.FirstPageLoading
            val items = safeApiCall(dispatcher) {
                collectionRepository.getCollections(page = 1)
            }
            uiState.value = UiState.Success(
                data = items,
                currentPage = 1,
                nextPageState = NextPageState.Idle,
            )
        }
    }

    override fun loadNextPage() {
        val state = uiState.value!!
        if (state !is BaseRefreshListUiState.Content) {
            return
        }

        when (state.nextPageState) {
            NextPageState.Done -> return
            NextPageState.Error -> return
            NextPageState.Loading -> return
            NextPageState.Idle -> {
                uiState.value = state.copy(nextPageState = NextPageState.Loading)

                viewModelScope.launch {
                    val nextPage = state.currentPage + 1

                    try {
                        val newItems = collectionRepository.getCollections(page = nextPage)
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
                        Log.e("ngc.tien", e.message.toString())
                        uiState.value = state.copy(nextPageState = NextPageState.Error)
                    }
                }
            }
        }
    }
}
