package com.ngc.tien.resplash.modules.collections

import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ngc.tien.resplash.data.remote.mapper.collection.Collection
import com.ngc.tien.resplash.data.remote.mapper.user.User
import com.ngc.tien.resplash.modules.collections.detail.CollectionDetailActivity
import com.ngc.tien.resplash.modules.core.BaseRefreshListFragment
import com.ngc.tien.resplash.modules.core.BaseRefreshListViewAdapter
import com.ngc.tien.resplash.modules.core.NetworkRequestEvent
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

@AndroidEntryPoint
class CollectionsFragment : BaseRefreshListFragment<Collection>() {
    override val viewModel by viewModels<CollectionsViewModel>()

    override fun initData() {
        var networkRequestEvent: NetworkRequestEvent = NetworkRequestEvent.Collections()
        arguments?.run {
            requireArguments().run {
                if (containsKey(KEY_SEARCH_QUERY)) {
                    networkRequestEvent = NetworkRequestEvent.Collections(
                        getString(KEY_SEARCH_QUERY)!!,
                        NetworkRequestEvent.Collections.Type.Search
                    )
                    binding.swipeRefreshLayout.isEnabled = false
                } else if (containsKey(KEY_USER_COLLECTIONS)) {
                    user = getSerializable(KEY_USER_COLLECTIONS, User::class.java)
                    networkRequestEvent = NetworkRequestEvent.Collections(
                        user?.userName ?: "",
                        NetworkRequestEvent.Collections.Type.GetByUser
                    )
                    binding.swipeRefreshLayout.isEnabled = false
                }
            }
        }
        viewModel.networkRequestEvent = networkRequestEvent
        super.initData()
    }

    override fun getAdapter(): BaseRefreshListViewAdapter = RecyclerViewAdapter(
        Glide.with(this@CollectionsFragment),
        ::handleUserClick,
        ::handleItemClick
    )

    private fun handleItemClick(collection: Collection, transitionImage: AppCompatImageView) {
        CollectionDetailActivity.launch(requireActivity(), collection)
    }

    companion object {
        private const val KEY_SEARCH_QUERY = "SEARCH_QUERY"
        const val KEY_USER_COLLECTIONS = "USER_COLLECTIONS"

        fun createFragment(key: String, query: Any): CollectionsFragment {
            return CollectionsFragment().apply {
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
