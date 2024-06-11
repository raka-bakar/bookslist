package com.raka.data.model

import com.squareup.moshi.Json

internal data class ProductResponse(

    @Json(name = "discountPercentage")
    val discountPercentage: Double,

    @Json(name = "thumbnail")
    val thumbnail: String,

    @Json(name = "images")
    val images: List<String>,

    @Json(name = "price")
    val price: Int,

    @Json(name = "rating")
    val rating: Double,

    @Json(name = "description")
    val description: String,

    @Json(name = "id")
    val id: Int,

    @Json(name = "title")
    val title: String,

    @Json(name = "stock")
    val stock: Int,

    @Json(name = "category")
    val category: String,

    @Json(name = "brand")
    val brand: String
)