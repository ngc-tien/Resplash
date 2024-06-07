package com.ngc.tien.resplash.modules.collections

import android.content.Intent
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ngc.tien.resplash.data.remote.mapper.collection.Collection
import com.ngc.tien.resplash.modules.collections.detail.CollectionDetailActivity
import com.ngc.tien.resplash.modules.core.BaseRefreshListFragment
import com.ngc.tien.resplash.modules.core.RequestType
import com.ngc.tien.resplash.util.IntentConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionsFragment : BaseRefreshListFragment() {
    override val recyclerViewAdapter by lazy(LazyThreadSafetyMode.NONE) {
        RecyclerViewAdapter(
            Glide.with(this@CollectionsFragment),
            ::handleUserClick,
            ::handleItemClick
        )
    }

    override val viewModel by viewModels<CollectionsViewModel>()

    override fun initData() {
        var requestType = RequestType.Home
        arguments?.run {
            requireArguments().run {
                if (containsKey(IntentConstants.KEY_SEARCH_QUERY)) {
                    requestType = RequestType.Search
                    requestType.query = getString(IntentConstants.KEY_SEARCH_QUERY)!!
                    binding.swipeRefreshLayout.isEnabled = false
                } else if (containsKey(IntentConstants.KEY_USER_COLLECTIONS)) {
                    requestType = RequestType.UserCollections
                    requestType.query = getString(IntentConstants.KEY_USER_COLLECTIONS)!!
                    binding.swipeRefreshLayout.isEnabled = false
                }
            }
        }
        viewModel.requestType = requestType
        super.initData()
    }

    private fun handleItemClick(
        collection: Collection,
        transitionImage: AppCompatImageView
    ) {
        Intent(requireActivity(), CollectionDetailActivity::class.java).run {
            putExtra(IntentConstants.KEY_COLLECTION, collection)
            startActivity(this)
        }
    }
}
