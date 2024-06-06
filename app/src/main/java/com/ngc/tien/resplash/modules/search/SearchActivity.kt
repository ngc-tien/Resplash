package com.ngc.tien.resplash.modules.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.databinding.ActivitySearchBinding
import com.ngc.tien.resplash.modules.collections.CollectionsFragment
import com.ngc.tien.resplash.modules.core.BaseViewPagerAdapter
import com.ngc.tien.resplash.modules.photo.PhotosFragment
import com.ngc.tien.resplash.util.Constants
import com.ngc.tien.resplash.util.IntentConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    private lateinit var viewPagerAdapter: BaseViewPagerAdapter
    private lateinit var searchString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchString = intent.getStringExtra(IntentConstants.KEY_SEARCH_QUERY) ?: ""
        initViews()
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

    @SuppressLint("WrongConstant")
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
        binding.viewPager.offscreenPageLimit = Constants.SEARCH_SCREEN_PAGE_LIMIT
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = viewPagerAdapter.getPageTitle(position)
        }.attach()
    }
}