package com.ngc.tien.resplash.modules.core

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

@SuppressLint("DiffUtilEquals")
object ItemCallBack :
    DiffUtil.ItemCallback<BaseRefreshListItem>() {
    override fun areItemsTheSame(
        oldItem: BaseRefreshListItem,
        newItem: BaseRefreshListItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: BaseRefreshListItem,
        newItem: BaseRefreshListItem
    ): Boolean {
        return oldItem == newItem
    }
}

open abstract class BaseRefreshListViewAdapter : ListAdapter<BaseRefreshListItem, BaseViewHolder>(ItemCallBack) {
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

open class BaseViewHolder(
    binding: ViewBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    open fun bind(item: BaseRefreshListItem) {

    }
}