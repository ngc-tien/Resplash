package com.ngc.tien.resplash.modules.user.search

import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.data.remote.mapper.user.User
import com.ngc.tien.resplash.modules.core.BaseRefreshListFragment
import com.ngc.tien.resplash.util.IntentConstants
import com.ngc.tien.resplash.util.helper.LauncherHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUsersFragment : BaseRefreshListFragment<User>() {
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
        transitionImage: AppCompatImageView
    ) {
        LauncherHelper.launchPhotoDetailPage(requireActivity(), photo, transitionImage)
    }
}
