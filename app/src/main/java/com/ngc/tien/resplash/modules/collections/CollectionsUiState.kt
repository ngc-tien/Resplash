package com.ngc.tien.resplash.modules.collections

sealed interface CollectionsUiState {
    data object FirstPageLoading : CollectionsUiState
    data object FirstPageError : CollectionsUiState
    data class Content(
        val items: List<CollectionItem>,
        val currentPage: Int,
        val nextPageState: NextPageState
    ) : CollectionsUiState

    sealed interface NextPageState {
        data object Loading : NextPageState
        data object Idle : NextPageState
        data object Error : NextPageState
        data object Done : NextPageState
    }
}