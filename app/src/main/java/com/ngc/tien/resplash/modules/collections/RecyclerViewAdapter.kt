package com.ngc.tien.resplash.modules.collections

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.RequestManager
import com.ngc.tien.resplash.databinding.CollectionItemViewLayoutBinding
import com.ngc.tien.resplash.modules.core.BaseRefreshListItem
import com.ngc.tien.resplash.modules.core.BaseRefreshListViewAdapter
import com.ngc.tien.resplash.modules.core.BaseViewHolder

class RecyclerViewAdapter(
    private val requestManager: RequestManager,
    private val onItemClick: (collectionItem: CollectionItem, transitionImage: AppCompatImageView) -> Unit
) : BaseRefreshListViewAdapter() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = CollectionItemViewLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(requestManager, binding, onItemClick)
    }

    class ViewHolder(
        private val requestManager: RequestManager,
        private val binding: CollectionItemViewLayoutBinding,
        private val onItemClick: (collectionItem: CollectionItem, transitionImage: AppCompatImageView) -> Unit
    ) :
        BaseViewHolder(binding) {
        override fun bind(item: BaseRefreshListItem) {
            (item as CollectionItem).run {
                binding.photoNumber.text = "$totalPhotos photos"
                binding.collectionName.text = title
                binding.userName.text = userName
                binding.coverImage.setAspectRatioAndColorForThumbnail(
                    coverWidth,
                    coverHeight,
                    coverColor
                )
                binding.userImage.setBackgroundColor(Color.parseColor(coverColor))
                requestManager
                    .load(userImage)
                    .into(binding.userImage)
                requestManager
                    .load(coverUrl)
                    .into(binding.coverImage)
                binding.coverImage.setOnClickListener {
                    onItemClick(item, binding.coverImage)
                }
            }

        }
    }
}