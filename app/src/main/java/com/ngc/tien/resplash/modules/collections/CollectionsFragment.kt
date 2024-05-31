package com.ngc.tien.resplash.modules.collections

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.data.remote.ResplashServiceLocator
import com.ngc.tien.resplash.databinding.CollectionsFragmentLayoutBinding
import com.ngc.tien.resplash.modules.core.BaseFragment
import com.ngc.tien.resplash.util.Constants.VISIBLE_ITEM_THRESHOLD
import com.ngc.tien.resplash.util.extentions.gone
import com.ngc.tien.resplash.util.extentions.visible

class CollectionsFragment :
    BaseFragment<CollectionsFragmentLayoutBinding>(inflate = CollectionsFragmentLayoutBinding::inflate) {

    private val recyclerViewAdapter by lazy(LazyThreadSafetyMode.NONE) {
        RecyclerViewAdapter(Glide.with(this@CollectionsFragment))
    }

    private val viewModel by viewModels<CollectionsViewModel>(
        factoryProducer = {
            viewModelFactory {
                addInitializer(CollectionsViewModel::class) {
                    CollectionsViewModel(
                        resplashApiService = ResplashServiceLocator.resplashApiService
                    )
                }
            }
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindingViewModel()
    }

    private fun initViews() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }
        binding.lottieLoading.setAnimation(R.raw.lottie_loading)
    }

    private fun bindingViewModel() {
        viewModel.uiStateLiveData.observe(viewLifecycleOwner, ::renderUiState)
        handleLoadMore()
    }

    private fun handleLoadMore() {
        val linearLayoutManager = binding.recyclerView.layoutManager as LinearLayoutManager

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0
                    && linearLayoutManager.findLastVisibleItemPosition() + VISIBLE_ITEM_THRESHOLD >= linearLayoutManager.itemCount
                ) {
                    viewModel.loadNextPage()
                }
            }
        })
    }

    private fun renderUiState(state: CollectionsUiState) {
        when (state) {
            is CollectionsUiState.Content -> {
                recyclerViewAdapter.submitList(state.items)
                binding.lottieLoading.pauseAnimation()
                binding.lottieLoading.gone()
            }

            CollectionsUiState.FirstPageError -> {
                recyclerViewAdapter.submitList(emptyList())
                binding.lottieLoading.pauseAnimation()
                binding.lottieLoading.gone()
            }

            CollectionsUiState.FirstPageLoading -> {
                binding.lottieLoading.playAnimation()
                binding.lottieLoading.repeatCount = LottieDrawable.INFINITE
                binding.lottieLoading.visible()
            }
        }
    }
}