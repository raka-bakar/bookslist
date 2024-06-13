package com.raka.data.network

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.net.SocketTimeoutException

internal class RetryNetworkInterceptor(private val retryAttempts: Int) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        for (i in 1..retryAttempts){
            try {
                return chain.proceed(chain.request())
            }catch (e: SocketTimeoutException){
                Timber.e(e)
            }
        }
        throw RuntimeException("Error compiling the request")
    }
}