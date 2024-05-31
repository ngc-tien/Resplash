package com.ngc.tien.resplash.modules.collections

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngc.tien.resplash.data.remote.ResplashApiService
import com.ngc.tien.resplash.data.remote.model.CollectionsResponseItem
import com.ngc.tien.resplash.modules.collections.CollectionsUiState.NextPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val resplashApiService: ResplashApiService
) : ViewModel() {
    private val _uiState = MutableLiveData<CollectionsUiState>(CollectionsUiState.FirstPageLoading)

    internal val uiStateLiveData: LiveData<CollectionsUiState> get() = _uiState

    init {
        loadFirstPage()
    }

    private fun loadFirstPage() {
        viewModelScope.launch {
            _uiState.value = CollectionsUiState.FirstPageLoading

            try {
                val items = resplashApiService
                    .getCollections(page = 1)
                    .map { it.toItem() }

                _uiState.value = CollectionsUiState.Content(
                    items = items,
                    currentPage = 1,
                    nextPageState = NextPageState.Idle,
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                Log.e("ngc.tien", e.message.toString())
                _uiState.value = CollectionsUiState.FirstPageError
            }
        }
    }

    fun loadNextPage() {
        val state = _uiState.value!!
        if (state !is CollectionsUiState.Content) {
            return
        }

        when (state.nextPageState) {
            NextPageState.Done -> return
            NextPageState.Error -> return
            NextPageState.Loading -> return
            NextPageState.Idle -> {
                _uiState.value = state.copy(nextPageState = NextPageState.Loading)

                viewModelScope.launch {
                    val nextPage = state.currentPage + 1

                    try {
                        val newItems = resplashApiService
                            .getCollections(page = nextPage)
                            .map { it.toItem() }

                        _uiState.value = state.copy(
                            items = (state.items + newItems).distinctBy { it.id },
                            currentPage = nextPage,
                            nextPageState = if (newItems.size < 10) {
                                NextPageState.Done
                            } else {
                                NextPageState.Idle
                            }
                        )
                    } catch (e: CancellationException) {
                        throw e
                    } catch (e: Throwable) {
                        Log.e("ngc.tien", e.message.toString())
                        _uiState.value = state.copy(nextPageState = NextPageState.Error)
                    }
                }
            }
        }
    }
}

private fun CollectionsResponseItem.toItem(): CollectionItem {
    return CollectionItem(
        id = id,
        title = title,
        totalPhotos = totalPhotos,
        userName = user.name,
        userImage = user.profileImage.medium,
        coverWidth = coverPhoto.width,
        coverHeight = coverPhoto.height,
        coverUrl = coverPhoto.urls.regular,
        coverColor = coverPhoto.color
    )
}