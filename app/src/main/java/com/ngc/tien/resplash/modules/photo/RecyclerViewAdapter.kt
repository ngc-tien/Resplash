package com.ngc.tien.resplash.modules.photo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.RequestManager
import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.data.remote.mapper.user.User
import com.ngc.tien.resplash.databinding.PhotoItemViewLayoutBinding
import com.ngc.tien.resplash.modules.core.BaseRefreshListItem
import com.ngc.tien.resplash.modules.core.BaseRefreshListViewAdapter
import com.ngc.tien.resplash.modules.core.BaseViewHolder

class RecyclerViewAdapter(
    private val requestManager: RequestManager,
    private val onUserClick: (user: User) -> Unit,
    private val onItemClick: (photo: Photo, transitionImage: AppCompatImageView) -> Unit
) : BaseRefreshListViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PhotoItemViewLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(requestManager, binding, onUserClick, onItemClick)
    }

    class ViewHolder(
        private val requestManager: RequestManager,
        private val binding: PhotoItemViewLayoutBinding,
        private val onUserClick: (user: User) -> Unit,
        private val onItemClick: (photo: Photo, transitionImage: AppCompatImageView) -> Unit
    ) :
        BaseViewHolder(binding) {
        override fun bind(item: BaseRefreshListItem) {
            (item as Photo).run {
                binding.txtUserName.text = user.name
                binding.photoImage.setAspectRatioAndColorForThumbnail(
                    width,
                    height,
                    color
                )
                binding.userInfo.setOnClickListener {
                    onUserClick(user)
                }
                binding.userImage.setBackgroundColor(Color.parseColor(color))
                requestManager
                    .load(user.profileImageMedium)
                    .into(binding.userImage)
                requestManager
                    .load(thumbnailUrl)
                    .into(binding.photoImage)
                itemView.setOnClickListener {
                    onItemClick(item, binding.photoImage)
                }
            }

        }
    }
}