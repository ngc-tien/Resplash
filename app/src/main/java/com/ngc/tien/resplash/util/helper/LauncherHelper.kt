package com.ngc.tien.resplash.util.helper

import android.app.Activity
import android.content.Intent
import com.ngc.tien.resplash.modules.search.SearchActivity
import com.ngc.tien.resplash.util.IntentConstants

object LauncherHelper {
    fun launchSearchPage(activity: Activity, query: String = "") {
        Intent(activity, SearchActivity::class.java).run {
            putExtra(IntentConstants.KEY_SEARCH_QUERY, query)
            activity.startActivity(this)
        }
    }
}