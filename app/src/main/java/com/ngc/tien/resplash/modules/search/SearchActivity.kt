package com.ngc.tien.resplash.modules.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.databinding.ActivitySearchBinding
import com.ngc.tien.resplash.modules.collections.CollectionsFragment
import com.ngc.tien.resplash.modules.core.BaseViewPagerAdapter
import com.ngc.tien.resplash.modules.photo.PhotosFragment
import com.ngc.tien.resplash.modules.user.search.SearchUsersFragment
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
    private var selectedPage = 0

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
        val searchUserFragment = SearchUsersFragment()
        val bundle = Bundle().apply {
            putString(IntentConstants.KEY_SEARCH_QUERY, searchString)
        }
        photosFragment.arguments = bundle
        collectionFragment.arguments = bundle
        searchUserFragment.arguments = bundle

        viewPagerAdapter = BaseViewPagerAdapter(this)
        viewPagerAdapter.addFragment(photosFragment, getString(R.string.photos))
        viewPagerAdapter.addFragment(collectionFragment, getString(R.string.collections))
        viewPagerAdapter.addFragment(searchUserFragment, getString(R.string.users))
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.offscreenPageLimit = Constants.SEARCH_SCREEN_PAGE_LIMIT
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = viewPagerAdapter.getPageTitle(position)
        }.attach()
        binding.viewPager.setCurrentItem(selectedPage, false)
        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                selectedPage = position
            }
        })
    }
}