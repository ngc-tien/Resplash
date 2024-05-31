package com.ngc.tien.resplash.modules.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngc.tien.resplash.data.remote.ResplashApiService
import com.ngc.tien.resplash.data.remote.model.PhotoResponseItem
import com.ngc.tien.resplash.modules.home.HomeUiState.NextPageState
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class HomeViewModel(private val resplashApiService: ResplashApiService) : ViewModel() {
    private val _uiState = MutableLiveData<HomeUiState>(HomeUiState.FirstPageLoading)

    internal val uiStateLiveData: LiveData<HomeUiState> get() = _uiState

    init {
        loadFirstPage()
    }

    private fun loadFirstPage() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.FirstPageLoading

            try {
                val items = resplashApiService
                    .getPhotos(page = 1)
                    .map { it.toItem() }

                _uiState.value = HomeUiState.Content(
                    items = items,
                    currentPage = 1,
                    nextPageState = NextPageState.Idle,
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                Log.e("ngc.tien", e.message.toString())
                _uiState.value = HomeUiState.FirstPageError
            }
        }
    }

    fun loadNextPage() {
        val state = _uiState.value!!
        if (state !is HomeUiState.Content) {
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
                            .getPhotos(page = nextPage)
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

private fun PhotoResponseItem.toItem(): PhotoItem {
    return PhotoItem(
        id = this.id,
        userName = user.name,
        userImage = user.profileImage.medium,
        photoWidth = width,
        photoHeight = height,
        photoUrl = urls.regular,
        photoColor = color
    )
}