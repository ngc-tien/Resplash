package com.ngc.tien.resplash.util.helper

import android.app.Activity
import android.content.Intent
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import com.ngc.tien.resplash.data.remote.mapper.photo.Photo
import com.ngc.tien.resplash.data.remote.mapper.user.User
import com.ngc.tien.resplash.modules.photo.detail.PhotoDetailActivity
import com.ngc.tien.resplash.modules.search.SearchActivity
import com.ngc.tien.resplash.modules.user.detail.UserDetailActivity
import com.ngc.tien.resplash.util.Constants
import com.ngc.tien.resplash.util.IntentConstants

object LauncherHelper {
    fun launchSearchPage(activity: Activity, query: String = "") {
        Intent(activity, SearchActivity::class.java).run {
            putExtra(IntentConstants.KEY_SEARCH_QUERY, query)
            activity.startActivity(this)
        }
    }

    fun launchUserDetailPage(activity: Activity, user: User) {
        Intent(activity, UserDetailActivity::class.java).run {
            putExtra(IntentConstants.KEY_USER, user)
            activity.startActivity(this)
        }
    }

    fun launchPhotoDetailPage(
        activity: Activity,
        photo: Photo,
        transitionImage: AppCompatImageView
    ) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            activity,
            transitionImage, Constants.SHARED_PHOTO_TRANSITION_NAME
        )
        Intent(activity, PhotoDetailActivity::class.java).apply {
            putExtra(IntentConstants.KEY_PHOTO, photo)
            activity.startActivity(this, options.toBundle())
        }
    }
}