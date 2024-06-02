package com.ngc.tien.resplash.modules.core

sealed interface BaseRefreshListUiState {
    data object FirstPageLoading : BaseRefreshListUiState
    data class FirstPageError(val message: String) : BaseRefreshListUiState
    data class Content(
        val items: List<BaseRefreshListItem>,
        val currentPage: Int,
        val nextPageState: NextPageState
    ) : BaseRefreshListUiState

    sealed interface NextPageState {
        data object Loading : NextPageState
        data object Idle : NextPageState
        data object Error : NextPageState
        data object Done : NextPageState
    }
}