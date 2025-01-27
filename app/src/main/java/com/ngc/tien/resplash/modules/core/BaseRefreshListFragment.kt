package com.ngc.tien.resplash.modules.core

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.lottie.LottieDrawable
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.data.remote.mapper.user.User
import com.ngc.tien.resplash.databinding.RefreshListItemFragmentLayoutBinding
import com.ngc.tien.resplash.modules.user.detail.UserDetailActivity
import com.ngc.tien.resplash.util.Constants
import com.ngc.tien.resplash.util.extentions.gone
import com.ngc.tien.resplash.util.extentions.pauseAndGone
import com.ngc.tien.resplash.util.extentions.playAndShow
import com.ngc.tien.resplash.util.extentions.visible

abstract class BaseRefreshListFragment<T : BaseRefreshListItem> :
    BaseFragment<RefreshListItemFragmentLayoutBinding>(
        RefreshListItemFragmentLayoutBinding::inflate
    ) {
    private var isRefreshing = false
    private var recyclerViewAdapter: BaseRefreshListViewAdapter? = null
    abstract val viewModel: BaseViewModel<T>
    internal var user: User? = null

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
        addObserves()
    }

    private fun initViews() {
        recyclerViewAdapter = getAdapter()
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                resources.getInteger(R.integer.number_of_column),
                LinearLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            adapter = recyclerViewAdapter
        }
        binding.lottieLoading.setAnimation(R.raw.lottie_loading)
        binding.lottieLoading.repeatCount = LottieDrawable.INFINITE
    }

    abstract fun initData()

    open fun addObserves() {
        handleLoadMore()
        viewModel.uiStateLiveData.observe(viewLifecycleOwner, ::renderUiState)
        binding.swipeRefreshLayout.setOnRefreshListener {
            if (viewModel.uiStateLiveData.value !is BaseRefreshListUiState.FirstPageLoading && !isRefreshing) {
                isRefreshing = true
                viewModel.refresh()
            }
        }
    }

    private fun handleLoadMore() {
        val layoutManager = binding.recyclerView.layoutManager as StaggeredGridLayoutManager
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastVisibleItems = layoutManager.findLastVisibleItemPositions(null)
                val lastVisibleItem = lastVisibleItems.maxOrNull() ?: 0
                if (dy > 0
                    && lastVisibleItem + Constants.VISIBLE_ITEM_THRESHOLD >= layoutManager.itemCount
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
        if (uiState.items.isEmpty()) {
            binding.errorState.visible()
            binding.errorStateMessage.text = getString(R.string.nothing_to_see_here)
        } else {
            binding.errorState.gone()
            recyclerViewAdapter?.submitList(uiState.items)
        }
    }

    open fun showFirstPageErrorState(uiState: BaseRefreshListUiState.FirstPageError) {
        isRefreshing = false
        binding.swipeRefreshLayout.isRefreshing = false
        binding.lottieLoading.pauseAndGone()
        binding.errorState.visible()
        binding.errorStateMessage.text = requireActivity().getString(uiState.messageResId)
        recyclerViewAdapter?.submitList(emptyList())
    }

    open fun showFirstPageLoadingState() {
        isRefreshing = false
        binding.swipeRefreshLayout.isRefreshing = false
        binding.lottieLoading.playAndShow()
        binding.errorState.gone()
        recyclerViewAdapter?.submitList(emptyList())
    }

    open fun handleUserClick(user: User) {
        if (this.user != null && this.user!!.id == user.id) {
            return
        }
        UserDetailActivity.launch(requireActivity(), user)
    }

    open fun onMapSharedElements(names: List<String?>, sharedElements: MutableMap<String?, View?>) {

    }

    override fun onDestroyView() {
        recyclerViewAdapter = null
        super.onDestroyView()
    }

    abstract fun getAdapter(): BaseRefreshListViewAdapter
}