package com.ngc.tien.resplash.modules.photo.wallpaper_settings

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.databinding.ActivityWallpaperSettingsBinding
import com.ngc.tien.resplash.util.IntentConstants
import com.ngc.tien.resplash.util.extentions.gone
import com.ngc.tien.resplash.util.extentions.visible
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WallpaperSettingsActivity : AppCompatActivity() {
    private lateinit var photo: Photo

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityWallpaperSettingsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
        initViews()
    }

    private fun initViews() {
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding.setWallpaperButton.setOnClickListener {
            binding.popupMenuWrapper.visible()
            slideUp(binding.popupMenu)
            it.gone()
        }
        binding.popupMenuWrapper.setOnClickListener {
            binding.popupMenuWrapper.gone()
            binding.setWallpaperButton.visible()
            slideDown(binding.popupMenu)
        }
        binding.homeScreenButton.setOnClickListener {
            setWallpaper(WallpaperManager.FLAG_SYSTEM)
        }
        binding.lockScreenButton.setOnClickListener {
            setWallpaper(WallpaperManager.FLAG_LOCK)
        }
        binding.homeAndLockScreenButton.setOnClickListener {
            setWallpaper(WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM)
        }
    }

    private fun loadData() {
        val photo = intent.getSerializableExtra(IntentConstants.KEY_PHOTO, Photo::class.java)
        if (photo == null) {
            finish()
        } else {
            this.photo = photo!!
            Glide.with(this)
                .load(photo.thumbnailRegularUrl)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(100, 3)))
                .into(binding.blurBackground)
            Glide.with(this)
                .asBitmap()
                .load(photo.thumbnailRegularUrl)
                .thumbnail(Glide.with(this).asBitmap().load(photo.thumbnailUrl))
                .into(object : BitmapImageViewTarget(binding.photoImage) {
                    override fun setResource(resource: Bitmap?) {
                        binding.photoImage.setBitmap(resource)
                    }
                })
        }
    }

    private fun setWallpaper(flag: Int) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    showMessage("Set wallpaper start")
                    val wallpaperManager = WallpaperManager.getInstance(applicationContext)
                    wallpaperManager.setBitmap(
                        binding.photoImage.getCurrentBitmap(),
                        null,
                        true,
                        flag
                    )
                    showMessage("Set wallpaper completed.")
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    Log.e(this::class.java.name, "set wallpaper failed ${ex.message}")
                    showMessage(getString(R.string.something_went_wrong))
                }
            }
        }
    }

    private suspend fun showMessage(message: String) {
        withContext(Dispatchers.Main) {
            Log.e("WallpaperSettings", "$message")
            Toast.makeText(this@WallpaperSettingsActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0f,  // fromXDelta
            0f,  // toXDelta
            view.height.toFloat(),  // fromYDelta
            0f
        ) // toYDelta
        animate.duration = 250
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    fun slideDown(view: View) {
        val animate = TranslateAnimation(
            0f,  // fromXDelta
            0f,  // toXDelta
            0f,  // fromYDelta
            view.height.toFloat()
        ) // toYDelta
        animate.duration = 250
        animate.fillAfter = true
        view.startAnimation(animate)
    }
}