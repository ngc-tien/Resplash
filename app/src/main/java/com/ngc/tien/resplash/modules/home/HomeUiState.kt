package com.ngc.tien.resplash.modules.home

sealed interface HomeUiState {
    data object FirstPageLoading : HomeUiState
    data object FirstPageError : HomeUiState
    data class Content(
        val items: List<PhotoItem>,
        val currentPage: Int,
        val nextPageState: NextPageState
    ) : HomeUiState

    sealed interface NextPageState {
        data object Loading : NextPageState
        data object Idle : NextPageState
        data object Error : NextPageState
        data object Done : NextPageState
    }
}