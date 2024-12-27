package com.ngc.tien.resplash.modules.user.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.data.remote.mapper.user.User
import com.ngc.tien.resplash.databinding.ActivityUserDetailBinding
import com.ngc.tien.resplash.modules.collections.CollectionsFragment
import com.ngc.tien.resplash.modules.core.BaseViewPagerActivity
import com.ngc.tien.resplash.modules.photo.PhotosFragment
import com.ngc.tien.resplash.util.Constants
import com.ngc.tien.resplash.util.extentions.gone
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : BaseViewPagerActivity() {
    private lateinit var user: User

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityUserDetailBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun loadData(savedInstanceState: Bundle?) {
        super.loadData(savedInstanceState)
        val user = intent.getSerializableExtra(KEY_USER, User::class.java)
        if (user == null) {
            finish()
        } else {
            this.user = user
            binding.toolbar.title = user.userName
            binding.name.text = user.name
            if (user.bio.isNotEmpty()) {
                binding.bio.text = user.bio
            } else {
                binding.bio.gone()
            }
            if (user.location.isNotEmpty()) {
                binding.location.text = user.location
            } else {
                binding.location.gone()
            }
            binding.totalLikes.text = user.totalLikes.toString()
            binding.totalPhotos.text = user.totalPhotos.toString()
            binding.totalCollections.text = user.totalCollections.toString()
            selectedPage(binding.photosContainer, 0)
            selectedPage(binding.likesContainer, 1)
            selectedPage(binding.collectionsContainer, 2)
            Glide.with(this).load(user.profileImageMedium).into(binding.profileImage)
            setupViewPager()
        }
    }

    private fun selectedPage(view: View, selectedIndex: Int) {
        view.setOnClickListener {
            binding.viewPager.setCurrentItem(selectedIndex, false)
        }
    }

    override fun getOffsetScreenPageLimit(): Int = Constants.SEARCH_SCREEN_PAGE_LIMIT

    override fun getViewPager(): ViewPager2 = binding.viewPager

    override fun getTabLayout(): TabLayout = binding.tabLayout

    override fun initFragments() {
        addUserPhotoFragment()
        if (user.totalLikes != 0) {
            addLikePhotosFragment()
        }
        if (user.totalCollections != 0) {
            addCollectionFragment()
        }
    }

    private fun addUserPhotoFragment() {
        val fragment = PhotosFragment.createFragment(PhotosFragment.KEY_USER_PHOTOS, user)
        viewPagerAdapter.addFragment(fragment, getString(R.string.photos))
    }

    private fun addLikePhotosFragment() {
        val fragment = PhotosFragment.createFragment(PhotosFragment.KEY_USER_LIKES, user)
        viewPagerAdapter.addFragment(fragment, getString(R.string.likes))
    }

    private fun addCollectionFragment() {
        val fragment =
            CollectionsFragment.createFragment(CollectionsFragment.KEY_USER_COLLECTIONS, user)
        viewPagerAdapter.addFragment(fragment, getString(R.string.collections))
    }

    companion object {
        private const val KEY_USER = "USER"

        fun launch(activity: Activity, user: User) {
            Intent(activity, UserDetailActivity::class.java).run {
                putExtra(KEY_USER, user)
                activity.startActivity(this)
            }
        }
    }
}