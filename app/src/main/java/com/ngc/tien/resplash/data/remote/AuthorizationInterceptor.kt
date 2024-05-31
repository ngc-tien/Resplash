package com.ngc.tien.resplash.data.remote

import com.ngc.tien.resplash.di.UnsplashApiAccessIdQualifier
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    @UnsplashApiAccessIdQualifier
    private val clientId: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain
        .request()
        .newBuilder()
        .addHeader("Authorization", "Client-ID $clientId")
        .build()
        .let(chain::proceed)
}