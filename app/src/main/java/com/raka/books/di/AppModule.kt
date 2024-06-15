package com.raka.books.di

import android.content.Context
import com.raka.books.BooksApp
import com.raka.data.DataProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun providesApplication(@ApplicationContext context: Context): BooksApp =
        context as BooksApp

    @Singleton
    @Provides
    fun provideDataProvider(@ApplicationContext context: Context): DataProvider {
        return DataProvider(context)
    }
}