package com.ngc.tien.resplash.modules.photo.detail

import com.ngc.tien.resplash.data.remote.mapper.photo.Photo


sealed interface PhotoDetailUIState {
    data object Loading : PhotoDetailUIState
    data class Content(val item: Photo) : PhotoDetailUIState
    data class Error(val messageResId: Int) : PhotoDetailUIState
}