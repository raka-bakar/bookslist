package com.raka.data.api

import com.raka.data.model.ResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    /**
     * load list of All books
     * @return Response] type of BooksResponse
     */
    @GET("1130fc01-ce29-4068-b2f4-ae886f725e69")
    suspend fun loadBooks(): Response<List<ResponseItem>>
}