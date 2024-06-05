package com.ngc.tien.resplash.modules.photo

import android.content.Intent
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.modules.core.BaseRefreshListFragment
import com.ngc.tien.resplash.modules.photo.detail.PhotoDetailActivity
import com.ngc.tien.resplash.util.Constants.SHARED_PHOTO_TRANSITION_NAME
import com.ngc.tien.resplash.util.IntentConstants
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO_COLOR
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO_HEIGHT
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO_ID
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO_URL
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO_WIDTH
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PhotosFragment : BaseRefreshListFragment() {
    override val recyclerViewAdapter by lazy(LazyThreadSafetyMode.NONE) {
        RecyclerViewAdapter(Glide.with(this@PhotosFragment),
            ::handleItemClick)
    }

    override val viewModel by viewModels<PhotosViewModel>()

    override fun initData() {
        var requestType = RequestType.Home
        arguments?.run {
            requireArguments().run {
                if (containsKey(IntentConstants.KEY_COLLECTION_ID)) {
                    requestType = RequestType.Collection
                    requestType.id = getString(IntentConstants.KEY_COLLECTION_ID)!!
                }
            }
        }
        viewModel.requestType = requestType
        super.initData()
    }

    private fun handleItemClick(photo: Photo, transitionImage: AppCompatImageView) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            transitionImage, SHARED_PHOTO_TRANSITION_NAME
        )
        Intent(requireActivity(), PhotoDetailActivity::class.java).apply {
            putExtra(KEY_PHOTO_ID, photo.id)
            putExtra(KEY_PHOTO_URL, photo.thumbnailUrl)
            putExtra(KEY_PHOTO_COLOR, photo.color)
            putExtra(KEY_PHOTO_WIDTH, photo.width)
            putExtra(KEY_PHOTO_HEIGHT, photo.height)
            startActivity(this, options.toBundle())
        }
    }
}