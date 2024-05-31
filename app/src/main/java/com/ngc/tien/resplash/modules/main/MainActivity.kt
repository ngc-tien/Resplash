package com.ngc.tien.resplash.modules.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.databinding.ActivityMainBinding
import com.ngc.tien.resplash.modules.collections.CollectionsFragment
import com.ngc.tien.resplash.modules.home.HomeFragment
import com.ngc.tien.resplash.util.IntentConstants
import com.ngc.tien.resplash.util.extentions.gone
import com.ngc.tien.resplash.util.extentions.transparent
import com.ngc.tien.resplash.util.extentions.visible

class MainActivity : AppCompatActivity() {
    private lateinit var showBottomBarBroadcastReceiver: BroadcastReceiver

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var viewPagerAdapter: MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
        registerListeners()
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

    private fun registerListeners() {
        showBottomBarBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.run {
                    val visible = getBooleanExtra(IntentConstants.KEY_VISIBLE, false)
                    if (visible) {
                        binding.fabAdd.transparent(false)
                        binding.bottomAppBar.visible()
                    } else {
                        binding.fabAdd.transparent(true)
                        binding.bottomAppBar.gone()
                    }
                }
            }
        }
        registerReceiver(
            showBottomBarBroadcastReceiver,
            IntentFilter(IntentConstants.ACTION_SET_BOTTOM_BAR_VISIBILITY),
            RECEIVER_EXPORTED
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(showBottomBarBroadcastReceiver)
    }
}