package com.ngc.tien.resplash.modules.collections.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.data.remote.mapper.collection.Collection
import com.ngc.tien.resplash.databinding.ActivityCollectionDetailBinding
import com.ngc.tien.resplash.modules.photo.PhotosFragment
import com.ngc.tien.resplash.util.IntentConstants
import com.ngc.tien.resplash.util.extentions.replaceFragment
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
    }
}