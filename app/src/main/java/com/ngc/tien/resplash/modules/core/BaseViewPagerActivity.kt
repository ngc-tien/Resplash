package com.ngc.tien.resplash.modules.core

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ngc.tien.resplash.util.Constants

abstract class BaseViewPagerActivity: AppCompatActivity() {
    companion object {
        private const val SELECTED_PAGE = "selected_page"
    }

    protected lateinit var viewPagerAdapter: BaseViewPagerAdapter
    private var selectedPage = 0

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        loadData(savedInstanceState)
        addObserves()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_PAGE, selectedPage)
    }

    @CallSuper
    open fun loadData(savedInstanceState: Bundle?) {
        selectedPage = savedInstanceState?.getInt(SELECTED_PAGE) ?: 0
    }

    abstract fun initViews()

    abstract fun getOffsetScreenPageLimit(): Int

    abstract fun getViewPager(): ViewPager2

    abstract fun getTabLayout(): TabLayout

    abstract fun initFragments()

    @SuppressLint("WrongConstant")
    fun setupViewPager() {
        val viewPager = getViewPager()
        val tabLayout = getTabLayout()
        viewPagerAdapter = BaseViewPagerAdapter(this)
        initFragments()
        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = Constants.SEARCH_SCREEN_PAGE_LIMIT
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = viewPagerAdapter.getPageTitle(position)
        }.attach()
        viewPager.setCurrentItem(selectedPage, false)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                selectedPage = position
            }
        })
    }

    private fun addObserves() {
        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: List<String?>, sharedElements: MutableMap<String?, View?>) {
                val fragment = (supportFragmentManager.findFragmentByTag("f" + viewPagerAdapter.getItemId(selectedPage))) as BaseRefreshListFragment<BaseRefreshListItem>
                fragment.onMapSharedElements(names, sharedElements)
            }
        })
    }
}