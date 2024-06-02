package com.ngc.tien.resplash.modules.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.RequestManager
import com.ngc.tien.resplash.databinding.HomeItemViewLayoutBinding
import com.ngc.tien.resplash.modules.core.BaseRefreshListItem
import com.ngc.tien.resplash.modules.core.BaseRefreshListViewAdapter
import com.ngc.tien.resplash.modules.core.BaseViewHolder

class RecyclerViewAdapter(
    private val requestManager: RequestManager,
    private val onItemClick: (photoItem: PhotoItem, transitionImage: AppCompatImageView) -> Unit
) : BaseRefreshListViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeItemViewLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(requestManager, binding, onItemClick)
    }

    class ViewHolder(
        private val requestManager: RequestManager,
        private val binding: HomeItemViewLayoutBinding,
        private val onItemClick: (photoItem: PhotoItem, transitionImage: AppCompatImageView) -> Unit
    ) :
        BaseViewHolder(binding) {
        override fun bind(item: BaseRefreshListItem) {
            (item as PhotoItem).run {
                binding.txtUserName.text = userName
                binding.photoImage.setAspectRatioAndColorForThumbnail(
                    photoWidth,
                    photoHeight,
                    photoColor
                )
                binding.userImage.setBackgroundColor(Color.parseColor(photoColor))
                requestManager
                    .load(userImage)
                    .into(binding.userImage)
                requestManager
                    .load(photoUrl)
                    .into(binding.photoImage)
                itemView.setOnClickListener {
                    onItemClick(item, binding.photoImage)
                }
            }

        }
    }
}