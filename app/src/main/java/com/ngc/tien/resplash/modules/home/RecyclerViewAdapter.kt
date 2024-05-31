package com.ngc.tien.resplash.modules.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ngc.tien.resplash.databinding.HomeItemViewLayoutBinding

private object ItemCallBack : DiffUtil.ItemCallback<PhotoItem>() {
    override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
        return oldItem == newItem
    }

}

class RecyclerViewAdapter(
    private val requestManager: RequestManager,
    private val onItemClick: (photoItem: PhotoItem, transitionImage: AppCompatImageView) -> Unit
) : ListAdapter<PhotoItem, RecyclerViewAdapter.ViewHolder>(ItemCallBack) {

    class ViewHolder(
        private val requestManager: RequestManager,
        private val binding: HomeItemViewLayoutBinding,
        private val onItemClick: (photoItem: PhotoItem, transitionImage: AppCompatImageView) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PhotoItem) {
            binding.txtUserName.text = item.userName
            binding.photoImage.setAspectRatioAndColorForThumbnail(item.photoWidth, item.photoHeight, item.photoColor)
            binding.userImage.setBackgroundColor(Color.parseColor(item.photoColor))
            requestManager
                .load(item.userImage)
                .into(binding.userImage)
            requestManager
                .load(item.photoUrl)
                .into(binding.photoImage)
            itemView.setOnClickListener {
                onItemClick(item, binding.photoImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HomeItemViewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(requestManager, binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position))
    }
}