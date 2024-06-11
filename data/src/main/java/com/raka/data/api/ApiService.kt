package com.raka.data.api

import com.raka.data.model.ApiResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

internal interface ApiService {
    /**
     * load list of All products
     * @return Single type of ApiResponse
     *
     */
    @GET("products")
    fun loadProducts(): Single<ApiResponse>
}