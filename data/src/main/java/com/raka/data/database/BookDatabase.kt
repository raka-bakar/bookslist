package com.raka.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raka.data.model.Book
import com.raka.data.utils.Constants

@Database(entities = [Book::class], version = Constants.DATABASE_VERSION, exportSchema = false)
internal abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}