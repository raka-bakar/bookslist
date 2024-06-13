package com.raka.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raka.data.utils.Constants

@Database(entities = [DBBook::class], version = Constants.DATABASE_VERSION, exportSchema = false)
internal abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}