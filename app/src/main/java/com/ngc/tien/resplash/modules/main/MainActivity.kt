package com.ngc.tien.resplash.modules.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionListenerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.databinding.ActivityMainBinding
import com.ngc.tien.resplash.modules.collections.CollectionsFragment
import com.ngc.tien.resplash.modules.home.HomeFragment
import com.ngc.tien.resplash.util.extentions.gone
import com.ngc.tien.resplash.util.extentions.transparent
import com.ngc.tien.resplash.util.extentions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var sharedExitTransitionListener: Transition.TransitionListener

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var viewPagerAdapter: MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
        setupWindowTransitionAnimation()
    }

    private fun setupViews() {
        viewPagerAdapter = MainViewPagerAdapter(this)
        viewPagerAdapter.addFragment(HomeFragment(), getString(R.string.home))
        viewPagerAdapter.addFragment(CollectionsFragment(), getString(R.string.collections))
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = viewPagerAdapter.getPageTitle(position)
        }.attach()
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