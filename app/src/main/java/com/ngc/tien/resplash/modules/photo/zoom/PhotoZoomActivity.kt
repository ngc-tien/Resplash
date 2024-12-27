package com.ngc.tien.resplash.modules.photo.zoom

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bumptech.glide.Glide
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.databinding.ActivityPhotoZoomBinding
import com.ngc.tien.resplash.util.Constants
import com.ngc.tien.resplash.util.ViewUtils
import com.ngc.tien.resplash.util.extentions.gone
import com.ngc.tien.resplash.util.extentions.visible


class PhotoZoomActivity : AppCompatActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityPhotoZoomBinding.inflate(layoutInflater)
    }
    private var showSystemBar = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadData()
        initViews()
        addListener()
    }

    private fun loadData() {
        intent?.run {
            Glide.with(this@PhotoZoomActivity)
                .load(getStringExtra(KEY_PHOTO_URL))
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
            showSystemBar = (windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars())
                    || windowInsets.isVisible(WindowInsetsCompat.Type.statusBars()))
            insets
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding.photoImage.setOnClickListener {
            if (showSystemBar) {
                windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
                binding.toolbarWrapper.gone()
            } else {
                windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
                binding.toolbarWrapper.visible()
            }
        }
    }

    private fun addListener() {
        onBackPressedDispatcher.addCallback {
            handleBackPressed()
        }
        binding.toolBar.setNavigationOnClickListener {
            handleBackPressed()
        }
        postponeEnterTransition()
        window.decorView.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                window.decorView.viewTreeObserver.removeOnPreDrawListener(this)
                startPostponedEnterTransition()
                return true
            }
        })
    }

    private fun handleBackPressed() {
        val layoutParams = binding.photoImage.layoutParams
        layoutParams.width = ViewUtils.getScreenWidth()
        layoutParams.height = resources.getDimensionPixelSize(R.dimen.photo_detail_image_height)
        binding.photoImage.scale = 1f
        binding.photoImage.scaleType = ImageView.ScaleType.CENTER_CROP
        binding.photoImage.layoutParams = layoutParams
        binding.photoImage.setImageDrawable(binding.photoImage.drawable)
        binding.photoImage.post(::finishAfterTransition)
    }

    companion object {
        private const val KEY_PHOTO_URL = "PHOTO_URL"

        fun launch(activity: Activity, transitionView: View, imageUrl: String) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                transitionView, Constants.SHARED_PHOTO_TRANSITION_NAME
            )
            Intent(activity, PhotoZoomActivity::class.java).apply {
                putExtra(KEY_PHOTO_URL, imageUrl)
                activity.startActivity(this, options.toBundle())
            }
        }
    }
}