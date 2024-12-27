package com.ngc.tien.resplash.modules.main

import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionListenerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.databinding.ActivityMainBinding
import com.ngc.tien.resplash.modules.collections.CollectionsFragment
import com.ngc.tien.resplash.modules.core.BaseViewPagerActivity
import com.ngc.tien.resplash.modules.photo.PhotosFragment
import com.ngc.tien.resplash.modules.search.SearchActivity
import com.ngc.tien.resplash.util.Constants
import com.ngc.tien.resplash.util.extentions.launchUrl
import com.ngc.tien.resplash.util.extentions.transparent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseViewPagerActivity() {
    private lateinit var sharedExitTransitionListener: Transition.TransitionListener

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWindowTransitionAnimation()
    }

    override fun initViews() {
        setContentView(binding.root)
        setupViewPager()
        binding.bottomAppBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_search -> SearchActivity.launch(this)
            }
            true
        }
        binding.fabAdd.setOnClickListener {
            launchUrl(getString(R.string.unsplash_unauthed_submit_url))
        }
    }

    override fun getOffsetScreenPageLimit(): Int = Constants.MAIN_OFF_SCREEN_PAGE_LIMIT

    override fun getViewPager(): ViewPager2 = binding.viewPager

    override fun getTabLayout(): TabLayout = binding.tabLayout

    override fun initFragments() {
        viewPagerAdapter.addFragment(PhotosFragment(), getString(R.string.home))
        viewPagerAdapter.addFragment(CollectionsFragment(), getString(R.string.collections))
    }

    private fun setupWindowTransitionAnimation() {
        sharedExitTransitionListener = object : TransitionListenerAdapter() {
            override fun onTransitionStart(transition: Transition?) {
                binding.bottomAppBar.transparent(true)
                binding.fabAdd.transparent(true)
                binding.tabLayout.transparent(true)
            }
        }
        window.sharedElementExitTransition.addListener(sharedExitTransitionListener)
    }

    override fun onResume() {
        super.onResume()
        binding.bottomAppBar.transparent(false)
        binding.fabAdd.transparent(false)
        binding.tabLayout.transparent(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        window.sharedElementExitTransition.removeListener(sharedExitTransitionListener)
    }
}