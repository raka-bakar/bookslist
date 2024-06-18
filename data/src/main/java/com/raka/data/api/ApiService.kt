package com.raka.data.api

import com.raka.data.model.ResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    /**
     * load list of All books
     * @return Response] type of BooksResponse
     */
    @GET(" ")
    suspend fun loadBooks(): Response<List<ResponseItem>>
}