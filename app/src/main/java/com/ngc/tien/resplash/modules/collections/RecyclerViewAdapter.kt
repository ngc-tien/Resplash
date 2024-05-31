package com.ngc.tien.resplash.modules.collections

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ngc.tien.resplash.databinding.CollectionItemViewLayoutBinding

object ItemCallBack :
    DiffUtil.ItemCallback<CollectionItem>() {
    override fun areItemsTheSame(oldItem: CollectionItem, newItem: CollectionItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CollectionItem, newItem: CollectionItem): Boolean {
        return oldItem == newItem
    }

}

class RecyclerViewAdapter(
    private val requestManager: RequestManager,
) : ListAdapter<CollectionItem, RecyclerViewAdapter.ViewHolder>(ItemCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CollectionItemViewLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(requestManager, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
        }
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val requestManager: RequestManager,
        private val binding: CollectionItemViewLayoutBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CollectionItem) {
            binding.photoNumber.text = "${item.totalPhotos} photos"
            binding.collectionName.text = item.title
            binding.userName.text = item.userName
            binding.coverImage.setAspectRatioAndColorForThumbnail(
                item.coverWidth,
                item.coverHeight,
                item.coverColor
            )
            binding.userImage.setBackgroundColor(Color.parseColor(item.coverColor))
            requestManager
                .load(item.userImage)
                .into(binding.userImage)
            requestManager
                .load(item.coverUrl)
                .into(binding.coverImage)
        }
    }
}