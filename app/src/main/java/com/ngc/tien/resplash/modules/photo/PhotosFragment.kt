package com.ngc.tien.resplash.modules.photo

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.data.remote.mapper.user.User
import com.ngc.tien.resplash.modules.core.BaseRefreshListFragment
import com.ngc.tien.resplash.modules.core.BaseRefreshListUiState
import com.ngc.tien.resplash.modules.core.RequestType
import com.ngc.tien.resplash.util.IntentConstants
import com.ngc.tien.resplash.util.helper.LauncherHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
open class PhotosFragment : BaseRefreshListFragment<Photo>() {
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
                    user = getSerializable(IntentConstants.KEY_USER_PHOTOS, User::class.java)
                    requestType = RequestType.UserPhotos
                    requestType.query = user!!.userName
                    binding.swipeRefreshLayout.isEnabled = false
                } else if (containsKey(IntentConstants.KEY_USER_LIKES)) {
                    user = getSerializable(IntentConstants.KEY_USER_LIKES, User::class.java)
                    requestType = RequestType.UserLikes
                    requestType.query = user!!.userName
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
        val uiState = viewModel.uiStateLiveData.value as BaseRefreshListUiState.Content
        val position = uiState.items.indexOf(photo)
        viewModel.selectedItemIndex = position
        LauncherHelper.launchPhotoDetailPage(requireActivity(), photo, transitionImage)
    }

    override fun onMapSharedElements(
        names: List<String?>,
        sharedElements: MutableMap<String?, View?>
    ) {
        if (viewModel.selectedItemIndex == -1) {
            return
        }
        binding.recyclerView.scrollToPosition(viewModel.selectedItemIndex)
        val selectedViewHolder: RecyclerView.ViewHolder = binding.recyclerView.findViewHolderForAdapterPosition(viewModel.selectedItemIndex)
            ?: return
        selectedViewHolder?.itemView?.run {
            sharedElements[names[0]] = findViewById(R.id.photoImage)
        }
    }
}