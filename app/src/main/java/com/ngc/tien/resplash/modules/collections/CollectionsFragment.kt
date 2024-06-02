package com.ngc.tien.resplash.modules.collections

import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ngc.tien.resplash.modules.core.BaseRefreshListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionsFragment : BaseRefreshListFragment() {
    override val recyclerViewAdapter by lazy(LazyThreadSafetyMode.NONE) {
        RecyclerViewAdapter(
            Glide.with(this@CollectionsFragment),
            ::handleItemClick
        )
    }

    override val viewModel by viewModels<CollectionsViewModel>()

    private fun handleItemClick(
        collectionItem: CollectionItem,
        transitionImage: AppCompatImageView
    ) {

    }
}