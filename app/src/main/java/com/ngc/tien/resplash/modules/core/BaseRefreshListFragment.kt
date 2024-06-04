package com.ngc.tien.resplash.modules.core

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.databinding.RefreshListItemFragmentLayoutBinding
import com.ngc.tien.resplash.util.Constants
import com.ngc.tien.resplash.util.extentions.gone
import com.ngc.tien.resplash.util.extentions.pauseAndGone
import com.ngc.tien.resplash.util.extentions.playAndShow
import com.ngc.tien.resplash.util.extentions.visible

abstract class BaseRefreshListFragment : BaseFragment<RefreshListItemFragmentLayoutBinding>(
    RefreshListItemFragmentLayoutBinding::inflate
) {
    private var isRefreshing = false
    abstract val recyclerViewAdapter: BaseRefreshListViewAdapter
    abstract val viewModel: IBaseRefreshListViewModel

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
        addObserves()
    }

    private fun initViews() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }
        binding.lottieLoading.setAnimation(R.raw.lottie_loading)
        binding.lottieLoading.repeatCount = LottieDrawable.INFINITE
    }

    private fun initData() {
        viewModel.loadFirstPage()
    }

    private fun addObserves() {
        handleLoadMore()
        viewModel.uiStateLiveData.observe(
            viewLifecycleOwner,
            ::renderUiState
        )
        binding.swipeRefreshLayout.setOnRefreshListener {
            if (viewModel.uiStateLiveData.value !is BaseRefreshListUiState.FirstPageLoading && !isRefreshing) {
                isRefreshing = true
                viewModel.loadFirstPage()
            }
        }
    }

    private fun handleLoadMore() {
        val linearLayoutManager = binding.recyclerView.layoutManager as LinearLayoutManager

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0
                    && linearLayoutManager.findLastVisibleItemPosition() + Constants.VISIBLE_ITEM_THRESHOLD >= linearLayoutManager.itemCount
                ) {
                    loadNextPage()
                }
            }
        })
    }

    private fun loadNextPage() {
        viewModel.loadNextPage()
    }

    open fun renderUiState(uiState: BaseRefreshListUiState) {
        when (uiState) {
            is BaseRefreshListUiState.Content -> loadContentSuccess(uiState)
            is BaseRefreshListUiState.FirstPageError -> showFirstPageErrorState(uiState)
            is BaseRefreshListUiState.FirstPageLoading -> showFirstPageLoadingState()
        }
    }

    open fun loadContentSuccess(uiState: BaseRefreshListUiState.Content) {
        isRefreshing = false
        binding.swipeRefreshLayout.isRefreshing = false
        binding.lottieLoading.pauseAndGone()
        binding.errorState.gone()
        recyclerViewAdapter.submitList(uiState.items)
    }

    open fun showFirstPageErrorState(uiState: BaseRefreshListUiState.FirstPageError) {
        isRefreshing = false
        binding.swipeRefreshLayout.isRefreshing = false
        binding.lottieLoading.pauseAndGone()
        binding.errorState.visible()
        binding.errorStateMessage.text = uiState.message
        recyclerViewAdapter.submitList(emptyList())
    }

    open fun showFirstPageLoadingState() {
        isRefreshing = false
        binding.swipeRefreshLayout.isRefreshing = false
        binding.lottieLoading.playAndShow()
        binding.errorState.gone()
        recyclerViewAdapter.submitList(emptyList())
    }
}