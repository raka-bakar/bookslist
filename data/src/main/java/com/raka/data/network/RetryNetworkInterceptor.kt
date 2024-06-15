package com.raka.data.network

import com.raka.data.utils.Constants
import com.raka.data.utils.Constants.RETRY_ATTEMPT_MAX
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

/**
 * Interceptor class to retry every failure network call
 * max retry attempt is 3 and delay between each call is 2 seconds
 */
internal class RetryNetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return processCall(chain, attempt = 1)
    }

    /**
     * function to check a response, if it is failure,
     * an attempt of retry will be executed until max attempt
     * @param chain of type Interceptor chain
     * @param attempt of type Int
     * @return Response of network call
     */
    private fun processCall(chain: Interceptor.Chain, attempt: Int): Response {
        Timber.d("network call attempt #$attempt")
        var response: Response? = null
        try {
            val request = chain.request()
            response = chain.proceed(request)
            if (attempt < RETRY_ATTEMPT_MAX && !response.isSuccessful) {
                return delayCall(chain, response, attempt)
            }
            return response
        } catch (error: Exception) {
            if (attempt < RETRY_ATTEMPT_MAX) {
                return delayCall(chain, response, attempt)
            }
            Timber.e(error)
            throw error
        }
    }

    /**
     * function delay between each call
     * @param chain of type Interceptor chain
     * @param response of type Response call
     * @param attempt of type Int
     * @return Response of network call
     */
    private fun delayCall(
        chain: Interceptor.Chain,
        response: Response?,
        attempt: Int
    ): Response {
        // if chain.proceed(request) is being called more than once,
        // previous response bodies must be closed
        response?.body?.close()
        Thread.sleep(Constants.DELAY_TIME_OUT * attempt)
        return processCall(chain, attempt + 1)
    }
}