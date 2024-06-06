package com.ngc.tien.resplash.modules.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.databinding.ActivitySearchBinding
import com.ngc.tien.resplash.modules.collections.CollectionsFragment
import com.ngc.tien.resplash.modules.core.BaseViewPagerAdapter
import com.ngc.tien.resplash.modules.photo.PhotosFragment
import com.ngc.tien.resplash.util.IntentConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    private lateinit var viewPagerAdapter: BaseViewPagerAdapter
    private var searchString: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchString = intent.getStringExtra(IntentConstants.KEY_SEARCH_QUERY)
        if (searchString == null) {
            finish()
        } else {
            initViews()
        }
    }

    private fun initViews() {
        setContentView(binding.root)
        setupViewPager()
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.searchText.setText(searchString)
        binding.searchText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchString = binding.searchText.text.toString()
                setupViewPager()
            }
            false
        }
    }

    private fun setupViewPager() {
        val photosFragment = PhotosFragment()
        val collectionFragment = CollectionsFragment()
        val bundle = Bundle().apply {
            putString(IntentConstants.KEY_SEARCH_QUERY, searchString)
        }
        photosFragment.arguments = bundle
        collectionFragment.arguments = bundle

        viewPagerAdapter = BaseViewPagerAdapter(this)
        viewPagerAdapter.addFragment(photosFragment, getString(R.string.photos))
        viewPagerAdapter.addFragment(collectionFragment, getString(R.string.collections))
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.offscreenPageLimit = 2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = viewPagerAdapter.getPageTitle(position)
        }.attach()
    }
}