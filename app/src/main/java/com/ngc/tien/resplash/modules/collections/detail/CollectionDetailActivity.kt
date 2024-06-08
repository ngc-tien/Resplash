package com.ngc.tien.resplash.modules.collections.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.data.remote.mapper.collection.Collection
import com.ngc.tien.resplash.databinding.ActivityCollectionDetailBinding
import com.ngc.tien.resplash.modules.photo.PhotosFragment
import com.ngc.tien.resplash.modules.user.UserDetailActivity
import com.ngc.tien.resplash.util.IntentConstants
import com.ngc.tien.resplash.util.extentions.launchUrl
import com.ngc.tien.resplash.util.extentions.replaceFragment
import com.ngc.tien.resplash.util.extentions.shareUrl
import com.ngc.tien.resplash.util.extentions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionDetailActivity : AppCompatActivity() {
    private lateinit var collection: Collection

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityCollectionDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initData()
        initViews()
    }

    private fun initData() {
        intent?.run {
            if (hasExtra(IntentConstants.KEY_COLLECTION)) {
                collection = intent.getSerializableExtra(
                    IntentConstants.KEY_COLLECTION,
                    Collection::class.java
                )!!
            } else {
                finish()
            }
        }
    }

    private fun initViews() {
        addPhotosFragment()
        initAppBar()
    }

    private fun addPhotosFragment() {
        val photosFragment = PhotosFragment()
        val bundle = Bundle()
        bundle.putString(IntentConstants.KEY_COLLECTION_ID, collection.id)
        photosFragment.arguments = bundle
        replaceFragment(photosFragment, false)
    }

    private fun initAppBar() {
        binding.toolBar.title = collection.title
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
        if (collection.description.isNotEmpty()) {
            binding.description.visible()
            binding.description.text = collection.description
        }
        binding.totalPhotos.text = collection.totalPhotos.toString()
        binding.user.text = collection.user.name
        binding.user.setOnClickListener {
            Intent(this, UserDetailActivity::class.java).run {
                putExtra(IntentConstants.KEY_USER, collection.user)
                startActivity(this)
            }
        }
    }
}
