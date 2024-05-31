package com.ngc.tien.resplash.modules.photo.detail


sealed interface PhotoDetailUIState {
    data object Loading : PhotoDetailUIState
    data class Content(val item: PhotoDetailItem) : PhotoDetailUIState
    data class Error(val message: String) : PhotoDetailUIState
}