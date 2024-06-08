package com.ngc.tien.resplash.modules.photo

import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.data.remote.mapper.user.User
import com.ngc.tien.resplash.modules.core.BaseRefreshListFragment
import com.ngc.tien.resplash.modules.core.RequestType
import com.ngc.tien.resplash.util.IntentConstants
import com.ngc.tien.resplash.util.helper.LauncherHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PhotosFragment : BaseRefreshListFragment() {
    override val recyclerViewAdapter by lazy(LazyThreadSafetyMode.NONE) {
        RecyclerViewAdapter(
            Glide.with(this@PhotosFragment),
            ::handleUserClick,
            ::handleItemClick
        )
    }

    override val viewModel by viewModels<PhotosViewModel>()

    override fun initData() {
        var requestType = RequestType.Home
        arguments?.run {
            requireArguments().run {
                if (containsKey(IntentConstants.KEY_COLLECTION_ID)) {
                    requestType = RequestType.Collection
                    requestType.query = getString(IntentConstants.KEY_COLLECTION_ID)!!
                } else if (containsKey(IntentConstants.KEY_SEARCH_QUERY)) {
                    requestType = RequestType.Search
                    requestType.query = getString(IntentConstants.KEY_SEARCH_QUERY)!!
                    binding.swipeRefreshLayout.isEnabled = false
                } else if (containsKey(IntentConstants.KEY_USER_PHOTOS)) {
                    requestType = RequestType.UserPhotos
                    requestType.query = getString(IntentConstants.KEY_USER_PHOTOS)!!
                    binding.swipeRefreshLayout.isEnabled = false
                } else if (containsKey(IntentConstants.KEY_USER_LIKES)) {
                    requestType = RequestType.UserLikes
                    requestType.query = getString(IntentConstants.KEY_USER_LIKES)!!
                    binding.swipeRefreshLayout.isEnabled = false
                }
            }
        }
        viewModel.requestType = requestType
        super.initData()
    }

    override fun handleUserClick(user: User) {
        if (viewModel.requestType == RequestType.UserPhotos || viewModel.requestType == RequestType.UserLikes) {
            if (viewModel.requestType.query != user.id) {
                super.handleUserClick(user)
            }
        } else {
            super.handleUserClick(user)
        }
    }

    private fun handleItemClick(photo: Photo, transitionImage: AppCompatImageView) {
        LauncherHelper.launchPhotoDetailPage(requireActivity(), photo, transitionImage)
    }

    override fun getCurrentUserId(): String {
        return if (viewModel.requestType == RequestType.UserPhotos || viewModel.requestType == RequestType.UserLikes) {
            viewModel.requestType.query
        } else {
            super.getCurrentUserId()
        }
    }
}