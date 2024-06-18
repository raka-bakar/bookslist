package com.raka.data.api

import com.raka.data.model.ResponseItem
import retrofit2.Response
import retrofit2.http.GET

internal interface ApiService {

    /**
     * load list of All books
     * @return Response type of list of ResponseItem
     */
    @GET(" ")
    suspend fun loadBooks(): Response<List<ResponseItem>>
}