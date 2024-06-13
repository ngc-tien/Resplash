package com.ngc.tien.resplash.modules.user.search

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
        user: User,
        photo: Photo,
        transitionImage: AppCompatImageView
    ) {
        val uiState = viewModel.uiStateLiveData.value as BaseRefreshListUiState.Content
        viewModel.selectedUseIndex = uiState.items.indexOf(user)
        viewModel.selectedPhotoIndex = user.photos.indexOf(photo)
        LauncherHelper.launchPhotoDetailPage(requireActivity(), photo, transitionImage)
    }

    override fun onMapSharedElements(
        names: List<String?>,
        sharedElements: MutableMap<String?, View?>
    ) {
        binding.recyclerView.scrollToPosition(viewModel.selectedUseIndex)
        val selectedViewHolder: RecyclerView.ViewHolder = binding.recyclerView.findViewHolderForAdapterPosition(viewModel.selectedUseIndex)
            ?: return
        selectedViewHolder?.itemView?.run {
            val listResId = listOf(R.id.photo1, R.id.photo2, R.id.photo3)
            sharedElements[names[0]] = findViewById(listResId[viewModel.selectedPhotoIndex])
        }
    }

    override fun onDestroyView() {
        requireActivity().setExitSharedElementCallback(null as android.app.SharedElementCallback?)
        super.onDestroyView()
    }
}