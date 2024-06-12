package com.ngc.tien.resplash.modules.user.search

import com.ngc.tien.resplash.data.remote.mapper.user.User
import com.ngc.tien.resplash.modules.core.BaseRefreshListItem
import com.ngc.tien.resplash.modules.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchUsersViewModel @Inject constructor() : BaseViewModel<User>() {
    var searchQuery = ""
    var selectedUseIndex = -1
    var selectedPhotoIndex = -1

    override suspend fun getData(page: Int): List<BaseRefreshListItem> {
        return searchRepository.searchUsers(searchQuery, page = 1)
    }
}