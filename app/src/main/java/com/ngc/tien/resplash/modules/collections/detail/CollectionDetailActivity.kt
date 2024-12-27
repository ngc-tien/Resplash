package com.ngc.tien.resplash.modules.collections.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.data.remote.mapper.collection.Collection
import com.ngc.tien.resplash.databinding.ActivityCollectionDetailBinding
import com.ngc.tien.resplash.modules.core.BaseViewPagerActivity
import com.ngc.tien.resplash.modules.photo.PhotosFragment
import com.ngc.tien.resplash.modules.user.detail.UserDetailActivity
import com.ngc.tien.resplash.util.extentions.launchUrl
import com.ngc.tien.resplash.util.extentions.shareUrl
import com.ngc.tien.resplash.util.extentions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionDetailActivity : BaseViewPagerActivity() {
    private lateinit var collection: Collection

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityCollectionDetailBinding.inflate(layoutInflater)
    }

    override fun loadData(savedInstanceState: Bundle?) {
        super.loadData(savedInstanceState)
        intent?.run {
            if (hasExtra(KEY_COLLECTION)) {
                collection = intent.getSerializableExtra(
                    KEY_COLLECTION,
                    Collection::class.java
                )!!
                binding.toolBar.title = collection.title
                if (collection.description.isNotEmpty()) {
                    binding.description.visible()
                    binding.description.text = collection.description
                }
                binding.totalPhotos.text = collection.totalPhotos.toString()
                binding.user.text = collection.user.name
                setupViewPager()
            } else {
                finish()
            }
        }
    }

    override fun initViews() {
        setContentView(binding.root)
        binding.toolBar.setNavigationOnClickListener {
            finish()
        }
        binding.toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.share -> collection.linkHtml.shareUrl(this@CollectionDetailActivity)
                R.id.viewInDetail -> launchUrl(collection.linkHtml)
                else -> {}
            }
            true
        }
        binding.user.setOnClickListener {
            UserDetailActivity.launch(this, collection.user)
        }
    }

    override fun getOffsetScreenPageLimit(): Int = 1

    override fun getViewPager(): ViewPager2 = binding.viewPager

    override fun getTabLayout(): TabLayout? = null

    override fun initFragments() {
        val photosFragment = PhotosFragment.createFragment(PhotosFragment.KEY_COLLECTION_ID, collection.id)
        viewPagerAdapter.addFragment(photosFragment, getString(R.string.photos))
    }

    companion object {
        private const val KEY_COLLECTION = "COLLECTION"

        fun launch(activity: Activity, collection: Collection) {
            Intent(activity, CollectionDetailActivity::class.java).run {
                putExtra(KEY_COLLECTION, collection)
                activity.startActivity(this)
            }
        }
    }
}
