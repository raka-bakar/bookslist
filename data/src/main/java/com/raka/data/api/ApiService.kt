package com.raka.data.api

import com.raka.data.CallResult
import com.raka.data.model.BookResponse
import com.raka.data.model.ResponseItem
import okhttp3.internal.toHeaderList
import retrofit2.Response
import retrofit2.http.GET

internal interface ApiService {
    /**
     * load list of All books
     * @return Response] type of BooksResponse
     */
    @GET("1130fc01-ce29-4068-b2f4-ae886f725e69")
    suspend fun loadBooks(): Response<List<ResponseItem>>

    companion object {
        /**
         * Helper method which can be used to safely do an API call from the interface
         * @param call the actual suspending network call
         * @return CallResult instance with transformed header and data
         */
        suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): CallResult<T> {
            try {
                val response = call.invoke()
                val body = response.body()
                if (response.isSuccessful) {
                    val headers = response.headers().toHeaderList().associate {
                        it.name.utf8() to it.value.utf8()
                    }
                    return CallResult.success(body, headers, "", response.code())
                }
                return CallResult.error(response.message(), response.code(), null)
            } catch (exception: Exception) {
                return CallResult.error(exception.message ?: exception.toString())
            }
        }
    }
}