package com.ngc.tien.resplash.modules.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.databinding.ActivityMainBinding
import com.ngc.tien.resplash.modules.collections.CollectionsFragment
import com.ngc.tien.resplash.modules.core.BaseViewPagerAdapter
import com.ngc.tien.resplash.modules.photo.PhotosFragment
import com.ngc.tien.resplash.util.Constants
import com.ngc.tien.resplash.util.extentions.launchUrl
import com.ngc.tien.resplash.util.extentions.transparent
import com.ngc.tien.resplash.util.helper.LauncherHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var sharedExitTransitionListener: Transition.TransitionListener

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var viewPagerAdapter: BaseViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
        setupWindowTransitionAnimation()
    }

    @SuppressLint("WrongConstant")
    private fun setupViews() {
        viewPagerAdapter = BaseViewPagerAdapter(this)
        viewPagerAdapter.addFragment(PhotosFragment(), getString(R.string.home))
        viewPagerAdapter.addFragment(CollectionsFragment(), getString(R.string.collections))
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.offscreenPageLimit = Constants.MAIN_OFF_SCREEN_PAGE_LIMIT
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = viewPagerAdapter.getPageTitle(position)
        }.attach()
        binding.bottomAppBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_search -> LauncherHelper.launchSearchPage(this@MainActivity)
            }
            true
        }
        binding.fabAdd.setOnClickListener {
            launchUrl(getString(R.string.unsplash_unauthed_submit_url))
        }
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