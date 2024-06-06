package com.ngc.tien.resplash.modules.photo.detail

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionListenerAdapter
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.forEach
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.google.android.material.chip.Chip
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.databinding.ActivityPhotoDetailBinding
import com.ngc.tien.resplash.modules.photo.zoom.PhotoZoomActivity
import com.ngc.tien.resplash.modules.search.SearchActivity
import com.ngc.tien.resplash.util.Constants
import com.ngc.tien.resplash.util.IntentConstants
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO_URL
import com.ngc.tien.resplash.util.ViewUtils
import com.ngc.tien.resplash.util.extentions.formatNumber
import com.ngc.tien.resplash.util.extentions.gone
import com.ngc.tien.resplash.util.extentions.launchUrl
import com.ngc.tien.resplash.util.extentions.pauseAndGone
import com.ngc.tien.resplash.util.extentions.playAndShow
import com.ngc.tien.resplash.util.extentions.shareUrl
import com.ngc.tien.resplash.util.extentions.transparent
import com.ngc.tien.resplash.util.extentions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailActivity : AppCompatActivity() {
    private lateinit var sharedEnterTransitionListener: Transition.TransitionListener
    private lateinit var sharedExitTransitionListener: Transition.TransitionListener
    private lateinit var wallpaperDownloadManager: WallpaperDownloadManager
    private lateinit var downloadReceiver: BroadcastReceiver
    private lateinit var photo: Photo
    private var onBackPressed: Boolean = false
    private var photoBitmap: Bitmap? = null

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityPhotoDetailBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<PhotoDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        loadData()
        addListeners()
        addObserves()
    }

    private fun loadData() {
        intent?.let {
            if (it.extras?.containsKey(KEY_PHOTO) == true) {
                photo = it.getSerializableExtra(KEY_PHOTO, Photo::class.java)!!
                binding.photoImage.setBackgroundColor(Color.parseColor(photo.color))
                binding.userImage.setBackgroundColor(Color.parseColor(photo.color))
                Glide.with(this)
                    .asBitmap()
                    .load(photo.thumbnailUrl)
                    .into(object : BitmapImageViewTarget(binding.photoImage) {
                        override fun setResource(resource: Bitmap?) {
                            photoBitmap = resource
                            super.setResource(resource)
                        }
                    })
                if (viewModel.uiState.value !is PhotoDetailUIState.Content) {
                    viewModel.getPhoto(photo.id)
                }
            } else {
                finish()
            }

        }
        wallpaperDownloadManager = WallpaperDownloadManager()
    }

    private fun addListeners() {
        binding.toolBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        onBackPressedDispatcher.addCallback {
            binding.locationWrapper.gone()
            binding.appBarLayout.gone()
            binding.photoDetail.gone()
            binding.photoImage.foreground = null
            onBackPressed = true
            window.sharedElementEnterTransition.removeListener(sharedEnterTransitionListener)
            finishAfterTransition()
            setPhotoImageFitToScreen()
            binding.photoImage.post(::finishAfterTransition)
        }
        binding.photoImage.setOnClickListener {
            setPhotoImageFitToScreen()
            binding.photoImage.post {
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    it, Constants.SHARED_PHOTO_TRANSITION_NAME
                )
                Intent(this, PhotoZoomActivity::class.java).apply {
                    putExtra(KEY_PHOTO_URL, photo.thumbnailUrl)
                    startActivity(this, options.toBundle())
                }
            }
        }
        binding.downloadPhoto.setOnClickListener {
            val uiState = viewModel.uiState.value as PhotoDetailUIState.Content
            val fileName = uiState.item.run {
                userName.lowercase().replace(" ", "-") + "-" + id + ".png"
            }
            val downloadId = wallpaperDownloadManager.downloadFile(
                this@PhotoDetailActivity,
                fileName,
                "Downloading $fileName",
                uiState.item.downloadPhotoUrl
            )
            Toast.makeText(this@PhotoDetailActivity, "Download started", Toast.LENGTH_SHORT).show()
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.errorState.gone()
            binding.photoDetail.gone()
            viewModel.getPhoto(photo.id)
        }
        addSharedWindowTransitionAnimation()
    }

    private fun setPhotoImageFitToScreen() {
        val params = binding.photoImage.layoutParams
        var photoWrapperWidth = ViewUtils.getScreenWidth()
        params.width = photoWrapperWidth
        params.height = ((photoWrapperWidth.toFloat() / photo.width) * photo.height).toInt()
        binding.photoImage.layoutParams = params
        if (photoBitmap != null) {
            binding.photoImage.setImageBitmap(photoBitmap!!)
        } else {
            binding.photoImage.setImageDrawable(binding.photoImage.drawable)
        }
        binding.photoImage.scaleType = ImageView.ScaleType.CENTER
    }

    private fun resetPhotoImageState() {
        val params = binding.photoImage.layoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = resources.getDimensionPixelSize(R.dimen.photo_detail_min_height)
        binding.photoImage.layoutParams = params
        if (photoBitmap != null) {
            binding.photoImage.setImageBitmap(photoBitmap!!)
        } else {
            binding.photoImage.setImageDrawable(binding.photoImage.drawable)
        }
        binding.photoImage.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    private fun addSharedWindowTransitionAnimation() {
        // shared enter/return is used to handle transition between PhotoDetail and HomeFragment
        // shared exit transition is used to handle transition between PhotoDetail and PhotoZoom
        sharedEnterTransitionListener = object : TransitionListenerAdapter() {
            override fun onTransitionEnd(transition: Transition?) {
                viewModel.isTransitionFinished = true
                renderUiState(viewModel.uiState.value)
            }
        }
        sharedExitTransitionListener = object : TransitionListenerAdapter() {
            override fun onTransitionStart(transition: Transition?) {
                binding.locationWrapper.transparent(true)
                binding.appBarLayout.transparent(true)
                binding.photoDetail.transparent(true)
            }
        }
        window.sharedElementEnterTransition.addListener(sharedEnterTransitionListener)
        window.sharedElementExitTransition.addListener(sharedExitTransitionListener)
    }

    private fun initViews() {
        setContentView(binding.root)
        binding.lottieLoading.apply {
            setAnimation(R.raw.lottie_loading)
            repeatCount = LottieDrawable.INFINITE;
        }
        binding.toolBar.menu.forEach { menu ->
            menu.icon?.setTint(resources.getColor(R.color.white, null))
        }
        binding.setWallpaperButton.setOnClickListener{
            viewModel.setWallpaper()
        }
        binding.toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.share -> photo.linkHtml.shareUrl(this@PhotoDetailActivity)
                R.id.viewInDetail -> launchUrl(photo.linkHtml)
                else -> {}
            }
            true
        }
    }

    private fun addObserves() {
        viewModel.uiState.observe(
            this,
            ::renderUiState
        )
        viewModel.setWallpaperMessage.observe(this, ::renderSetWallpaperMessage)
        downloadReceiver = object : BroadcastReceiver() {
            @SuppressLint("Range")
            override fun onReceive(context: Context, intent: Intent) {
                val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                var message = ""
                if (downloadId == -1L) {
                    message = "Download failed"
                } else {
                    val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                    downloadManager.query(DownloadManager.Query().setFilterById(downloadId))?.use {
                        it.moveToNext()
                        val status: Int =
                            it.getInt(it.getColumnIndex(DownloadManager.COLUMN_STATUS))
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            message = "Download completed"
                        } else if (status == DownloadManager.STATUS_FAILED) {
                            message = "Download failed"
                        }
                    }
                }
                if (message.isNotEmpty()) {
                    Toast.makeText(this@PhotoDetailActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        registerReceiver(
            downloadReceiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
            RECEIVER_EXPORTED
        )
    }

    private fun renderUiState(uiState: PhotoDetailUIState?) {
        when (uiState) {
            is PhotoDetailUIState.Content -> renderPhotoDetail(uiState)
            is PhotoDetailUIState.Error -> {
                binding.swipeRefreshLayout.isRefreshing = false
                binding.lottieLoading.pauseAndGone()
                binding.errorState.visible()
                binding.appBarLayout.visible()
                binding.errorStateMessage.text = uiState.message
            }
            is PhotoDetailUIState.Loading -> binding.lottieLoading.playAndShow()
            else -> {}
        }
    }

    private fun renderPhotoDetail(uiState: PhotoDetailUIState.Content) {
        if (!viewModel.isTransitionFinished) {
            binding.photoDetail.gone()
            return
        }

        val item = uiState.item
        binding.photoDetail.visible()
        binding.appBarLayout.visible()
        binding.errorState.gone()
        binding.lottieLoading.pauseAndGone()
        binding.swipeRefreshLayout.isRefreshing = false
        if (item.location.isNotEmpty()) {
            binding.locationWrapper.visible()
            binding.locationName.text = item.location
        } else {
            binding.locationWrapper.gone()
        }
        Glide.with(this)
            .load(item.userImage)
            .into(binding.userImage)
        item.run {
            binding.userName.text = userName
            binding.txtCamera.text = camInfo
            binding.txtAperture.text = aperture
            binding.txtFocalLength.text = focalLength
            binding.txtShutterSpeed.text = shutterSpeed
            binding.txtISO.text = item.iso
            binding.txtDimentions.text = "${item.width} x ${item.height}"
            binding.txtTotalViews.text = totalViews.formatNumber()
            binding.txtTotalDownloads.text = totalDownloads.formatNumber()
            binding.txtTotalLikes.text = totalLikes.formatNumber()
            for (tag in tags) {
                val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                layoutParams.marginEnd =
                    this@PhotoDetailActivity.resources.getDimensionPixelSize(R.dimen.dimen_8dp)
                val chip = Chip(this@PhotoDetailActivity).apply {
                    text = tag
                }
                binding.tags.addView(chip, layoutParams)
                chip.setOnClickListener {
                    handleTagClicked(tag)
                }
            }
        }
    }

    private fun renderSetWallpaperMessage(message: String) {
        if (message.isNotEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleTagClicked(tag: String) {
        Intent(this@PhotoDetailActivity, SearchActivity::class.java).run {
            putExtra(IntentConstants.KEY_SEARCH_QUERY, tag)
            startActivity(this)
        }
    }

    override fun onStart() {
        super.onStart()
        resetPhotoImageState()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.uiState.value !is PhotoDetailUIState.Loading) {
            binding.lottieLoading.gone()
        }
        binding.locationWrapper.transparent(false)
        binding.appBarLayout.transparent(false)
        binding.photoDetail.transparent(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
        window.sharedElementEnterTransition.removeListener(sharedEnterTransitionListener)
        window.sharedElementExitTransition.removeListener(sharedExitTransitionListener)
    }
}