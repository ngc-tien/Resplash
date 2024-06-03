package com.ngc.tien.resplash.modules.photo.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngc.tien.resplash.data.remote.ResplashApiService
import com.ngc.tien.resplash.data.remote.mapper.photo.toItem
import com.ngc.tien.resplash.data.remote.repositories.photo.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {
    private val _uiState = MutableLiveData<PhotoDetailUIState>()

    val uiState: LiveData<PhotoDetailUIState> get() = _uiState

    var isTransitionFinished = false

    fun getPhoto(id: String) {
        viewModelScope.launch {
            _uiState.value = PhotoDetailUIState.Loading
            try {
                val response = photoRepository.getPhotosById(id)
                _uiState.value = PhotoDetailUIState.Content(response)
            } catch (ex: Exception) {
                _uiState.value = PhotoDetailUIState.Error(ex.message.toString())
            }
        }
    }
}