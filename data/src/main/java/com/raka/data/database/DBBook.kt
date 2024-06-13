package com.raka.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Table "book" in local database
 */
@Entity(tableName = "book")
data class DBBook(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "image")
    val image: String = "",
    @ColumnInfo(name = "release_date")
    val releaseDate: String = "",
    @ColumnInfo(name = "author")
    val author: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "title")
    val title: String = ""
)