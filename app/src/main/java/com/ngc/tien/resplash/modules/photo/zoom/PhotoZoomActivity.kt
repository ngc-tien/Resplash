package com.ngc.tien.resplash.modules.photo.zoom

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bumptech.glide.Glide
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.databinding.ActivityPhotoZoomBinding
import com.ngc.tien.resplash.util.IntentConstants

class PhotoZoomActivity : AppCompatActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityPhotoZoomBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadIntentData()
        initViews()
        addListener()
    }

    private fun loadIntentData() {
        intent?.run {
            Glide.with(this@PhotoZoomActivity)
                .load(getStringExtra(IntentConstants.KEY_PHOTO_URL))
                .into(binding.photoImage)
        }
    }

    private fun initViews() {
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, windowInsets ->
            val insets = ViewCompat.onApplyWindowInsets(view, windowInsets)
            if (windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars())
                || windowInsets.isVisible(WindowInsetsCompat.Type.statusBars())
            ) {
                binding.photoImage.setOnClickListener {
                    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
                }
            } else {
                binding.photoImage.setOnClickListener {
                    windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
                }
            }
            insets
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun addListener() {
        onBackPressedDispatcher.addCallback {
            val layoutParams = binding.photoImage.layoutParams
            layoutParams.height = resources.getDimensionPixelSize(R.dimen.photo_detail_min_height)
            binding.photoImage.scale = 1f
            binding.photoImage.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.photoImage.layoutParams = layoutParams
            binding.photoImage.setImageDrawable(binding.photoImage.drawable)
            binding.photoImage.post(::finishAfterTransition)
        }
    }
}