package com.ngc.tien.resplash.modules.photo.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngc.tien.resplash.data.remote.ResplashApiService
import com.ngc.tien.resplash.data.remote.model.photo.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val resplashApiService: ResplashApiService
) : ViewModel() {
    private val _uiState = MutableLiveData<PhotoDetailUIState>()

    val uiState: LiveData<PhotoDetailUIState> get() = _uiState

    var isTransitionFinished = false

    fun getPhoto(id: String) {
        viewModelScope.launch {
            _uiState.value = PhotoDetailUIState.Loading
            try {
                val photoDetailResponse = resplashApiService.getPhotosById(id)
                _uiState.value = PhotoDetailUIState.Content(photoDetailResponse.toItem())
            } catch (ex: Exception) {
                _uiState.value = PhotoDetailUIState.Error(ex.message.toString())
            }
        }
    }
}

fun Photo.toItem(): PhotoDetailItem {
    return PhotoDetailItem(
        id = id,
        userName = user?.name ?: "",
        userImage = user?.profileImage?.medium ?: "",
        location = location?.name ?: "",
        camInfo = exif?.name ?: "Unknown",
        aperture = exif?.aperture ?: "Unknown",
        focalLength = exif?.focalLength ?: "Unknown",
        shutterSpeed = exif?.exposureTime ?: "Unknown",
        iso = exif?.iso?.toString() ?: "Unknown",
        width = width ?: 0,
        height = height ?: 0,
        downloadPhotoUrl = urls.full,
        totalViews = views ?: 0,
        totalDownloads = downloads ?: 0,
        totalLikes = likes ?: 0,
        tags = tags?.map {
            it.title
        } ?: emptyList()
    )
}