package com.ngc.tien.resplash.di

import com.ngc.tien.resplash.BuildConfig
import com.ngc.tien.resplash.data.remote.AuthorizationInterceptor
import com.ngc.tien.resplash.data.remote.ResplashApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UnsplashApiAccessIdQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UnsplashBaseUrlQualifier

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {
    companion object {
        private const val UNSPLASH_BASE_URL = "https://api.unsplash.com/"

        @Provides
        @Singleton
        fun moshi(): Moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        @Provides
        fun httpLoggingInterceptor() = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        @Provides
        @UnsplashApiAccessIdQualifier
        fun provideUnsplashAccessId(): String = BuildConfig.UNSPLASH_ACCESS_KEY

        @Provides
        @UnsplashBaseUrlQualifier
        fun provideBaseUrl(): String = UNSPLASH_BASE_URL

        @Provides
        @Singleton
        fun okHttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
            authorizationInterceptor: AuthorizationInterceptor,
        ): OkHttpClient =
            OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(httpLoggingInterceptor)
                .addInterceptor(authorizationInterceptor)
                .build()

        @Provides
        @Singleton
        fun retrofit(
            moshi: Moshi,
            okHttpClient: OkHttpClient,
            @UnsplashBaseUrlQualifier baseUrl: String
        ): Retrofit =
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

        @Provides
        @Singleton
        fun unsplashApiService(
            retrofit: Retrofit
        ): ResplashApiService = ResplashApiService(retrofit)

    }
}