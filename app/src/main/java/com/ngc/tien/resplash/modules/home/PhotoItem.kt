package com.ngc.tien.resplash.modules.home

import com.ngc.tien.resplash.modules.core.BaseRefreshListItem

data class PhotoItem(
    override val id: String,
    val userImage: String,
    val userName: String,
    val photoWidth: Int,
    val photoHeight: Int,
    val photoUrl: String,
    val photoColor: String,
) : BaseRefreshListItem()
