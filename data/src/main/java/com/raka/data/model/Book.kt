package com.raka.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Table "book" in local database
 */
@Entity(tableName = "book")
data class Book(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "image")
    val image: String = "",
    @ColumnInfo(name = "release_date")
    var releaseDate: String = "",
    @ColumnInfo(name = "author")
    val author: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "title")
    val title: String = ""
)