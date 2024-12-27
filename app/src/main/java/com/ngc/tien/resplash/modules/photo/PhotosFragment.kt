package com.ngc.tien.resplash.modules.photo

import android.os.Bundle
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
import com.ngc.tien.resplash.modules.core.BaseRefreshListViewAdapter
import com.ngc.tien.resplash.modules.core.NetworkRequestEvent
import com.ngc.tien.resplash.modules.core.NetworkRequestEvent.Photo.Type.*
import com.ngc.tien.resplash.modules.photo.detail.PhotoDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable


@AndroidEntryPoint
open class PhotosFragment : BaseRefreshListFragment<Photo>() {

    override val viewModel by viewModels<PhotosViewModel>()

    override fun initData() {
        var networkRequestEvent: NetworkRequestEvent = NetworkRequestEvent.Photo()
        arguments?.run {
            requireArguments().run {
                if (containsKey(KEY_COLLECTION_ID)) {
                    networkRequestEvent = NetworkRequestEvent.Photo(getString(KEY_COLLECTION_ID)!!, Collections)
                } else if (containsKey(KEY_SEARCH_QUERY)) {
                    networkRequestEvent = NetworkRequestEvent.Photo(getString(KEY_SEARCH_QUERY)!!, Search)
                    binding.swipeRefreshLayout.isEnabled = false
                } else if (containsKey(KEY_USER_PHOTOS)) {
                    user = getSerializable(KEY_USER_PHOTOS, User::class.java)
                    networkRequestEvent = NetworkRequestEvent.Photo(user!!.userName, UserPhotos)
                    binding.swipeRefreshLayout.isEnabled = false
                } else if (containsKey(KEY_USER_LIKES)) {
                    user = getSerializable(KEY_USER_LIKES, User::class.java)
                    networkRequestEvent = NetworkRequestEvent.Photo(user!!.userName, UserLikes)
                    binding.swipeRefreshLayout.isEnabled = false
                }
            }
        }
        viewModel.networkRequestEvent = networkRequestEvent
        super.initData()
    }

    override fun getAdapter(): BaseRefreshListViewAdapter = RecyclerViewAdapter(
        Glide.with(this@PhotosFragment),
        ::handleUserClick,
        ::handleItemClick
    )

    override fun handleUserClick(user: User) {
        val userNetworkRequestEvent = viewModel.networkRequestEvent as NetworkRequestEvent.Photo
        if (userNetworkRequestEvent.type == UserPhotos || userNetworkRequestEvent.type == UserLikes) {
            if (userNetworkRequestEvent.queryString != user.id) {
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
        PhotoDetailActivity.launch(requireActivity(), photo, transitionImage)
    }

    override fun onMapSharedElements(
        names: List<String?>,
        sharedElements: MutableMap<String?, View?>
    ) {
        if (viewModel.selectedItemIndex == -1) {
            return
        }
        binding.recyclerView.scrollToPosition(viewModel.selectedItemIndex)
        val selectedViewHolder: RecyclerView.ViewHolder =
            binding.recyclerView.findViewHolderForAdapterPosition(viewModel.selectedItemIndex)
                ?: return
        selectedViewHolder?.itemView?.run {
            sharedElements[names[0]] = findViewById(R.id.photoImage)
        }
    }

    companion object {
        const val KEY_SEARCH_QUERY = "SEARCH_QUERY"
        const val KEY_COLLECTION_ID = "COLLECTION_ID"
        const val KEY_USER_PHOTOS = "USER_PHOTOS"
        const val KEY_USER_LIKES = "USER_LIKES"

        fun createFragment(key: String, query: Any): PhotosFragment {
            return PhotosFragment().apply {
                val bundle = Bundle().apply {
                    when (query) {
                        is Serializable -> putSerializable(key, query)
                        is String -> putString(key, query)
                        else -> {}
                    }
                }
                arguments = bundle
            }
        }
    }
}