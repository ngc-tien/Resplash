package com.ngc.tien.resplash.modules.photo.detail

import android.app.WallpaperManager
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngc.tien.resplash.data.remote.repositories.photo.PhotoRepository
import com.ngc.tien.resplash.util.getErrorMessage
import com.squareup.picasso.Picasso
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                _uiState.value = PhotoDetailUIState.Error(getErrorMessage(context, ex))
            }
        }
    }

    fun setWallpaper() {
        viewModelScope.launch {
           withContext(Dispatchers.IO) {
               try {
                   _setWallpaperMessage.postValue("Set wallpaper start.")
                   val uiState = uiState.value as PhotoDetailUIState.Content
                   val bitmap = Picasso.get()
                       .load(uiState.item.downloadPhotoUrl)
                       .get()
                   val wallpaperManager = WallpaperManager.getInstance(context)
                   wallpaperManager.setBitmap(bitmap)
                   _setWallpaperMessage.postValue("Set wallpaper completed.")
               } catch (ex: Exception) {
                   ex.printStackTrace()
                   Log.e(this::class.java.name, "set wallpaper failed ${ex.message}")
                   _setWallpaperMessage.postValue("set wallpaper failed.")
               }
               _setWallpaperMessage.postValue("")
           }
        }
    }
}