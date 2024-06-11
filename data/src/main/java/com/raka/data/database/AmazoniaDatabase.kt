package com.raka.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raka.data.utils.Constants

@TypeConverters(value = [TypeConverter::class])
@Database(entities = [DBProduct::class], version = Constants.DATABASE_VERSION, exportSchema = false)
internal abstract class AmazoniaDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}