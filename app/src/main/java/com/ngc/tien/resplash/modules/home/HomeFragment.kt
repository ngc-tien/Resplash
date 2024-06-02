package com.ngc.tien.resplash.modules.home

import android.content.Intent
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ngc.tien.resplash.modules.core.BaseRefreshListFragment
import com.ngc.tien.resplash.modules.photo.detail.PhotoDetailActivity
import com.ngc.tien.resplash.util.Constants.SHARED_PHOTO_TRANSITION_NAME
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO_COLOR
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO_HEIGHT
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO_ID
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO_URL
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO_WIDTH
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseRefreshListFragment() {
    override val recyclerViewAdapter by lazy(LazyThreadSafetyMode.NONE) {
        RecyclerViewAdapter(Glide.with(this@HomeFragment),
            ::handleItemClick)
    }

    override val viewModel by viewModels<HomeViewModel>()


    private fun handleItemClick(photoItem: PhotoItem, transitionImage: AppCompatImageView) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            transitionImage, SHARED_PHOTO_TRANSITION_NAME
        )
        Intent(requireActivity(), PhotoDetailActivity::class.java).apply {
            putExtra(KEY_PHOTO_ID, photoItem.id)
            putExtra(KEY_PHOTO_URL, photoItem.photoUrl)
            putExtra(KEY_PHOTO_COLOR, photoItem.photoColor)
            putExtra(KEY_PHOTO_WIDTH, photoItem.photoWidth)
            putExtra(KEY_PHOTO_HEIGHT, photoItem.photoHeight)
            startActivity(this, options.toBundle())
        }
    }
}