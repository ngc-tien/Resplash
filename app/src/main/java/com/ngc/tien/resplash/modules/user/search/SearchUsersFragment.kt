package com.ngc.tien.resplash.modules.user.search

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
import com.ngc.tien.resplash.modules.photo.detail.PhotoDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUsersFragment : BaseRefreshListFragment<User>() {

    override val viewModel by viewModels<SearchUsersViewModel>()

    override fun initData() {
        var searchQuery = ""
        arguments?.run {
            requireArguments().run {
                if (containsKey(KEY_SEARCH_QUERY)) {
                    searchQuery = getString(KEY_SEARCH_QUERY)!!
                }
            }
        }
        viewModel.loadFirstPage(searchQuery)
    }

    override fun getAdapter(): BaseRefreshListViewAdapter = RecyclerViewAdapter(
        Glide.with(this@SearchUsersFragment),
        ::handleUserClick,
        ::handlePhotoClick
    )

    private fun handlePhotoClick(
        user: User,
        photo: Photo,
        transitionImage: AppCompatImageView
    ) {
        val uiState = viewModel.uiStateLiveData.value as BaseRefreshListUiState.Content
        viewModel.selectedUseIndex = uiState.items.indexOf(user)
        viewModel.selectedPhotoIndex = user.photos.indexOf(photo)
        PhotoDetailActivity.launch(requireActivity(), photo, transitionImage)
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

    companion object {
        private const val KEY_SEARCH_QUERY = "SEARCH_QUERY"

        fun createFragment(queryString: String) : SearchUsersFragment {
            return SearchUsersFragment().apply {
                val bundle = Bundle().apply {
                    putString(KEY_SEARCH_QUERY, queryString)
                }
                arguments = bundle
            }
        }
    }
}