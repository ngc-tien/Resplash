package com.ngc.tien.resplash.modules.photo.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngc.tien.resplash.data.remote.repositories.photo.PhotoRepository
import com.ngc.tien.resplash.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val photoRepository: PhotoRepository
) : ViewModel() {
    private val _uiState = MutableLiveData<PhotoDetailUIState>()
    val uiState: LiveData<PhotoDetailUIState> get() = _uiState

    private val _setWallpaperMessage = MutableLiveData("")
    val setWallpaperMessage: LiveData<String> get() = _setWallpaperMessage

    var isTransitionFinished = false
    var isTransitionWork = false

    fun getPhoto(id: String) {
        viewModelScope.launch {
            _uiState.value = PhotoDetailUIState.Loading
            try {
                val photo = photoRepository.getPhotosById(id)
                _uiState.value = PhotoDetailUIState.Content(photo)
            } catch (ex: Exception) {
                _uiState.value = PhotoDetailUIState.Error(getErrorMessage(ex))
            }
        }
    }
}