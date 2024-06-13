package com.raka.data.di

import android.content.Context
import androidx.room.Room
import com.raka.data.database.BookDatabase
import com.raka.data.database.BookDao
import com.raka.data.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DatabaseModule {
    /**
     * provides room database instance
     * @param context
     */
    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): BookDatabase {
        return Room.databaseBuilder(
            context,
            BookDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    /**
     * provides room dao
     * @param booksDatabase of type BookDatabase
     */
    @Provides
    @Singleton
    fun providesProductsDao(booksDatabase: BookDatabase): BookDao {
        return booksDatabase.bookDao()
    }
}