package com.ngc.tien.resplash.modules.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.data.remote.ResplashServiceLocator
import com.ngc.tien.resplash.databinding.HomeFragmentLayoutBinding
import com.ngc.tien.resplash.modules.core.BaseFragment
import com.ngc.tien.resplash.modules.photo.detail.PhotoDetailActivity
import com.ngc.tien.resplash.util.Constants.SHARED_PHOTO_TRANSITION_NAME
import com.ngc.tien.resplash.util.Constants.VISIBLE_ITEM_THRESHOLD
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO_COLOR
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO_HEIGHT
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO_ID
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO_URL
import com.ngc.tien.resplash.util.IntentConstants.KEY_PHOTO_WIDTH
import com.ngc.tien.resplash.util.extentions.gone
import com.ngc.tien.resplash.util.extentions.visible


class HomeFragment :
    BaseFragment<HomeFragmentLayoutBinding>(inflate = HomeFragmentLayoutBinding::inflate) {

    private val recyclerViewAdapter by lazy(LazyThreadSafetyMode.NONE) {
        RecyclerViewAdapter(Glide.with(this@HomeFragment),
            ::handleItemClick)
    }

    private val viewModel by viewModels<HomeViewModel>(
        factoryProducer = {
            viewModelFactory {
                addInitializer(HomeViewModel::class) {
                    HomeViewModel(
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
        viewModel.uiStateLiveData.observe(viewLifecycleOwner,
            ::renderUiState)
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

    private fun renderUiState(state: HomeUiState) {
        when (state) {
            is HomeUiState.Content -> {
                recyclerViewAdapter.submitList(state.items)
                binding.lottieLoading.pauseAnimation()
                binding.lottieLoading.gone()
            }

            HomeUiState.FirstPageError -> {
                recyclerViewAdapter.submitList(emptyList())
                binding.lottieLoading.pauseAnimation()
                binding.lottieLoading.gone()
            }

            HomeUiState.FirstPageLoading -> {
                binding.lottieLoading.playAnimation()
                binding.lottieLoading.repeatCount = LottieDrawable.INFINITE
                binding.lottieLoading.visible()
            }
        }
    }

    private fun handleItemClick(photoItem: PhotoItem, transitionImage: AppCompatImageView) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            transitionImage, SHARED_PHOTO_TRANSITION_NAME
        )
        Intent(requireActivity(), PhotoDetailActivity::class.java).apply {
            putExtra(KEY_PHOTO_ID, photoItem.id)
            putExtra(KEY_PHOTO_URL, photoItem.photoUrl)
            putExtra(KEY_PHOTO_COLOR, photoItem.photoColor)
            putExtra(KEY_PHOTO_WIDTH, photoItem.photoWidth)
            putExtra(KEY_PHOTO_HEIGHT, photoItem.photoHeight)
            startActivity(this, options.toBundle())
        }
    }
}