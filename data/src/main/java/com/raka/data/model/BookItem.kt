package com.raka.data.model

/**
 * Data of Book which will be shown in the list
 */
data class BookItem(
    val id: Int,
    val title: String,
    val description: String,
    val image: String
)