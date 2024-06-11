package com.raka.data.network

import com.raka.data.BuildConfig
import com.raka.data.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                Constants.USER_AGENT,
                "${BuildConfig.APP_NAME} : ${BuildConfig.APP_VERSION}"
            )
            .build()

        return chain.proceed(request)
    }
}