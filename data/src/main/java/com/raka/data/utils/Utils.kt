package com.raka.data.utils

import com.raka.data.database.DBProduct
import com.raka.data.model.ProductResponse

internal fun ProductResponse.toDBProduct(productResponse: ProductResponse): DBProduct {
    return (
        DBProduct(
            id = productResponse.id,
            brand = productResponse.brand,
            category = productResponse.category,
            description = productResponse.description,
            discountPercentage = productResponse.discountPercentage,
            images = productResponse.images,
            isFavorite = false,
            price = productResponse.price,
            rating = productResponse.rating,
            stock = productResponse.stock,
            thumbnail = productResponse.thumbnail,
            title = productResponse.title
        )
        )
}