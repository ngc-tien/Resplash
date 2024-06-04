package com.ngc.tien.resplash.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.http.HttpException
import com.ngc.tien.resplash.R
import java.io.IOException
import java.lang.Exception
import java.net.UnknownHostException

@SuppressLint("NewApi")
fun getErrorMessage(context: Context, exception: Exception) : String{
    return when (exception) {
        is HttpException,
        is IOException,
        is UnknownHostException -> context.resources.getString(R.string.unable_to_connect_to_the_server)
        else -> context.resources.getString(R.string.something_went_wrong)
    }
}