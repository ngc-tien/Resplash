package com.ngc.tien.resplash.modules.user.search

import android.content.Intent
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.modules.core.BaseRefreshListFragment
import com.ngc.tien.resplash.modules.photo.detail.PhotoDetailActivity
import com.ngc.tien.resplash.util.IntentConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUsersFragment : BaseRefreshListFragment() {
    override val recyclerViewAdapter by lazy(LazyThreadSafetyMode.NONE) {
        RecyclerViewAdapter(
            Glide.with(this@SearchUsersFragment),
            ::handleUserClick,
            ::handlePhotoClick
        )
    }

    override val viewModel by viewModels<SearchUsersViewModel>()

    override fun initData() {
        var searchQuery = ""
        arguments?.run {
            requireArguments().run {
                if (containsKey(IntentConstants.KEY_SEARCH_QUERY)) {
                    searchQuery = getString(IntentConstants.KEY_SEARCH_QUERY)!!
                }
            }
        }
        viewModel.searchQuery = searchQuery
        super.initData()
    }

    private fun handlePhotoClick(
        photo: Photo,
    ) {
        Intent(requireActivity(), PhotoDetailActivity::class.java).apply {
            putExtra(IntentConstants.KEY_PHOTO, photo)
            startActivity(this)
        }
    }
}
