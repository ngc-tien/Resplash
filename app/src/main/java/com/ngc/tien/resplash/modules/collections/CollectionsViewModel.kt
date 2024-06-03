package com.ngc.tien.resplash.modules.collections

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngc.tien.resplash.data.remote.ResplashApiService
import com.ngc.tien.resplash.data.remote.model.collection.CollectionsResponse
import com.ngc.tien.resplash.modules.core.BaseRefreshListUiState
import com.ngc.tien.resplash.modules.core.BaseRefreshListUiState.NextPageState
import com.ngc.tien.resplash.modules.core.IBaseRefreshListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val resplashApiService: ResplashApiService
) : ViewModel(), IBaseRefreshListViewModel {
    private val uiState =
        MutableLiveData<BaseRefreshListUiState>(BaseRefreshListUiState.FirstPageLoading)
    override val uiStateLiveData: LiveData<BaseRefreshListUiState> get() = uiState

    override fun loadFirstPage() {
        viewModelScope.launch {
            uiState.value = BaseRefreshListUiState.FirstPageLoading

            try {
                val items = resplashApiService
                    .getCollections(page = 1)
                    .map { it.toItem() }

                uiState.value = BaseRefreshListUiState.Content(
                    items = items,
                    currentPage = 1,
                    nextPageState = NextPageState.Idle,
                )
            }  catch (e: Exception) {
                Log.e("ngc.tien", e.message.toString())
                uiState.value = BaseRefreshListUiState.FirstPageError(e.message.toString())
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
            NextPageState.Error -> return
            NextPageState.Loading -> return
            NextPageState.Idle -> {
                uiState.value = state.copy(nextPageState = NextPageState.Loading)

                viewModelScope.launch {
                    val nextPage = state.currentPage + 1

                    try {
                        val newItems = resplashApiService
                            .getCollections(page = nextPage)
                            .map { it.toItem() }

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

private fun CollectionsResponse.toItem(): CollectionItem {
    return CollectionItem(
        id = id,
        title = title,
        totalPhotos = totalPhotos,
        userName = user.name,
        userImage = user.profileImage.medium,
        coverWidth = coverPhoto?.width ?: 0,
        coverHeight = coverPhoto?.height ?: 0,
        coverUrl = coverPhoto.urls.regular,
        coverColor = coverPhoto.color ?: "#E0E0E0"
    )
}