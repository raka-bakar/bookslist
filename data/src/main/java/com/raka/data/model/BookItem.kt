package com.raka.data.model

import androidx.room.ColumnInfo

/**
 * Data of Book which will be shown in the list
 */
data class BookItem(
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "image")
    val image: String = ""
)