package com.ngc.tien.resplash.util

import android.content.res.Resources

object ViewUtils {
    fun getScreenWidth() : Int{
        return Resources.getSystem().displayMetrics.widthPixels
    }
}