package com.ngc.tien.resplash.modules.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.databinding.ActivitySearchBinding
import com.ngc.tien.resplash.modules.collections.CollectionsFragment
import com.ngc.tien.resplash.modules.core.BaseViewPagerActivity
import com.ngc.tien.resplash.modules.photo.PhotosFragment
import com.ngc.tien.resplash.modules.user.search.SearchUsersFragment
import com.ngc.tien.resplash.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseViewPagerActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<SearchViewModel>()

    override fun initViews() {
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.searchText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchString = binding.searchText.text.toString()
                setupViewPager()
            }
            false
        }
    }

    override fun loadData(savedInstanceState: Bundle?) {
        super.loadData(savedInstanceState)
        viewModel.searchString = intent.getStringExtra(KEY_SEARCH_QUERY) ?: ""
        setupViewPager()
        binding.searchText.setText(viewModel.searchString)
    }

    override fun getOffsetScreenPageLimit(): Int = Constants.SEARCH_SCREEN_PAGE_LIMIT

    override fun getViewPager(): ViewPager2 = binding.viewPager

    override fun getTabLayout(): TabLayout = binding.tabLayout

    override fun initFragments() {
        val photosFragment = PhotosFragment.createFragment(PhotosFragment.KEY_SEARCH_QUERY, viewModel.searchString)
        val collectionFragment = CollectionsFragment.createFragment(CollectionsFragment.KEY_USER_COLLECTIONS, viewModel.searchString)
        val searchUserFragment = SearchUsersFragment.createFragment(viewModel.searchString)
        viewPagerAdapter.addFragment(photosFragment, getString(R.string.photos))
        viewPagerAdapter.addFragment(collectionFragment, getString(R.string.collections))
        viewPagerAdapter.addFragment(searchUserFragment, getString(R.string.users))
    }

    companion object {
        private const val KEY_SEARCH_QUERY = "SEARCH_QUERY"

        fun launch(activity: Activity, query: String = "") {
            Intent(activity, SearchActivity::class.java).run {
                putExtra(KEY_SEARCH_QUERY, query)
                activity.startActivity(this)
            }
        }
    }
}