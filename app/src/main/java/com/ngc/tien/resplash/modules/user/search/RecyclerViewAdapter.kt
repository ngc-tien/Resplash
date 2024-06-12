package com.ngc.tien.resplash.modules.user.search

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.RequestManager
import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.data.remote.mapper.user.User
import com.ngc.tien.resplash.databinding.SearchUserItemLayoutBinding
import com.ngc.tien.resplash.modules.core.BaseRefreshListItem
import com.ngc.tien.resplash.modules.core.BaseRefreshListViewAdapter
import com.ngc.tien.resplash.modules.core.BaseViewHolder
import com.ngc.tien.resplash.util.extentions.gone
import com.ngc.tien.resplash.util.extentions.visible

class RecyclerViewAdapter(
    private val requestManager: RequestManager,
    private val onUserClick: (user: User) -> Unit,
    private val onPhotoClick: (user: User, photo: Photo, transitionImage: AppCompatImageView) -> Unit
) : BaseRefreshListViewAdapter() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = SearchUserItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(requestManager, binding, onUserClick, onPhotoClick)
    }

    class ViewHolder(
        private val requestManager: RequestManager,
        private val binding: SearchUserItemLayoutBinding,
        private val onUserClick: (user: User) -> Unit,
        private val onPhotoClick: (user: User, photo: Photo, transitionImage: AppCompatImageView) -> Unit
    ) :
        BaseViewHolder(binding) {
        override fun bind(item: BaseRefreshListItem) {
            (item as User).run {
                binding.name.text = name.trim()
                binding.userName.text = "@${userName}"
                binding.root.setOnClickListener {
                    onUserClick(this)
                }
                binding.userImage.setBackgroundColor(Color.parseColor("#E0E0E0"))
                requestManager
                    .load(profileImageLarge)
                    .into(binding.userImage)
                if (photos == null || photos!!.size < 3) {
                    binding.photos.gone()
                } else {
                    binding.photos.visible()
                    bindingPhoto(item, binding.photo1, photos[0])
                    bindingPhoto(item, binding.photo2, photos[1])
                    bindingPhoto(item, binding.photo3, photos[2])
                }
            }
        }

        private fun bindingPhoto(user: User, imageView: AppCompatImageView, photo: Photo) {
            requestManager
                .load(photo.thumbnailSmallUrl)
                .thumbnail(requestManager.load(photo.thumbnailUrl))
                .into(imageView)
            imageView.setBackgroundColor(Color.parseColor("#E0E0E0"))
            imageView.setOnClickListener{
                onPhotoClick(user, photo, imageView)
            }
        }
    }
}