package com.raka.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "product")
data class DBProduct(
    @PrimaryKey
    val id: Int,
    val discountPercentage: Double,
    val thumbnail: String,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val description: String,
    val title: String,
    val stock: Int,
    val category: String,
    val brand: String,
    val isFavorite: Boolean
)