package com.ngc.tien.resplash.data.remote

import android.app.Application
import androidx.annotation.MainThread
import com.ngc.tien.resplash.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ResplashServiceLocator {
    private const val UNSPLASH_BASE_URL = "https://api.unsplash.com/"

    @set:MainThread
    @get:MainThread
    private var _application: Application? = null

    @MainThread
    fun initWith(app: Application) {
        _application = app
    }

    @get:MainThread
    val application: Application
        get() = checkNotNull(_application) {
            "UnsplashServiceLocator must be initialized. " +
                    "Call UnsplashServiceLocator.initWith(this) in your Application class."
        }

    // ------------------------------------------------------------

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    private val httpLoggingInterceptor
        get() = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }


    private val authorizationInterceptor: AuthorizationInterceptor
        get() = AuthorizationInterceptor(
            clientId = BuildConfig.UNSPLASH_ACCESS_KEY
        )

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(UNSPLASH_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor(authorizationInterceptor)
            .build()
    }

    val resplashApiService: ResplashApiService by lazy { ResplashApiService(retrofit) }
}