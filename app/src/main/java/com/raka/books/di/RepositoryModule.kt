package com.raka.books.di

import com.raka.books.repository.BookRepository
import com.raka.books.repository.BookRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger providers for Repository components
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    /**
     * provides BookRepository
     * @param impl of BookRepositoryImpl type
     * @return BookRepository instance
     */
    @Binds
    @Singleton
    abstract fun provideBookRepository(impl: BookRepositoryImpl)
            : BookRepository
}