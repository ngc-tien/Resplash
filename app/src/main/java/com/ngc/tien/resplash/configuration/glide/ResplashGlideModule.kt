package com.ngc.tien.resplash.configuration.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.io.InputStream

@GlideModule
class ResplashGlideModule: AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)

        val entryPoint: UnsplashGlideModuleEntryPoint = EntryPointAccessors
            .fromApplication<UnsplashGlideModuleEntryPoint>(context)

        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(
                entryPoint.okHttpClient
            )
        )
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface UnsplashGlideModuleEntryPoint {
        val okHttpClient: OkHttpClient
    }
}