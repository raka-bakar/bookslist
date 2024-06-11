package com.raka.data.model

import com.squareup.moshi.Json

internal data class ApiResponse(

    @Json(name = "total")
    val total: Int,

    @Json(name = "limit")
    val limit: Int,

    @Json(name = "skip")
    val skip: Int,

    @Json(name = "products")
    val productResponses: List<ProductResponse>
)