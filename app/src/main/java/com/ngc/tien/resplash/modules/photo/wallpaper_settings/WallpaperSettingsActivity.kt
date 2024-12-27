package com.ngc.tien.resplash.modules.photo.wallpaper_settings

import android.app.Activity
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.Target
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.databinding.ActivityWallpaperSettingsBinding
import com.ngc.tien.resplash.modules.photo.detail.PhotoDetailActivity.Companion.KEY_PHOTO
import com.ngc.tien.resplash.util.extentions.gone
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WallpaperSettingsActivity : AppCompatActivity() {
    private lateinit var photo: Photo
    private val uiHandler = Handler(Looper.getMainLooper())
    private lateinit var modalBottomSheet: SetWallpaperBottomSheet
    private var thumbnailLoaded = 0

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
            modalBottomSheet.setCallBack(object : SetWallpaperBottomSheet.CallBack {
                override fun setWallpaper(flag: Int) {
                    this@WallpaperSettingsActivity.setWallpaper(flag)
                }
            })
            modalBottomSheet.show(supportFragmentManager, SetWallpaperBottomSheet.TAG)
        }
        modalBottomSheet =
            if (supportFragmentManager.findFragmentByTag(SetWallpaperBottomSheet.TAG) != null) {
                supportFragmentManager.findFragmentByTag(SetWallpaperBottomSheet.TAG)!! as SetWallpaperBottomSheet
            } else {
                SetWallpaperBottomSheet()
            }
    }

    private fun loadData() {
        val photo = intent.getSerializableExtra(KEY_PHOTO, Photo::class.java)
        if (photo == null) {
            finish()
        } else {
            this.photo = photo!!
            window.decorView.setBackgroundColor(Color.parseColor(photo.color))
            binding.blurBackground.setBackgroundColor(Color.parseColor(photo.color))
            Glide.with(this)
                .load(photo.thumbnailUrl)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(100, 3)))
                .into(binding.blurBackground)
            val requestThumbnail = Glide.with(this)
                .asBitmap()
                .load(photo.thumbnailUrl)
                .addListener(object :
                    RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.photoImage.setBitmap(resource)
                        return true
                    }

                })
            Glide.with(this)
                .asBitmap()
                .load(photo.thumbnailRegularUrl)
                .thumbnail(requestThumbnail)
                .into(object : BitmapImageViewTarget(binding.photoImage) {
                    override fun setResource(resource: Bitmap?) {
                        if (resource == null) {
                            return
                        }
                        binding.photoImage.setBitmap(resource)
                        binding.photoImageWrapper.cardElevation =
                            resources.getDimensionPixelSize(R.dimen.dimen_8dp).toFloat()
                        binding.loading.gone()
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

    companion object {
        private const val KEY_PHOTO = "PHOTO"

        fun launch(activity: Activity, photo: Photo) {
            Intent(activity, WallpaperSettingsActivity::class.java).apply {
                putExtra(KEY_PHOTO, photo)
                activity.startActivity(this)
            }
        }
    }
}