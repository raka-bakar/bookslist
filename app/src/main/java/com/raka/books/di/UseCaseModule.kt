package com.raka.books.di

import com.raka.books.usecase.GetBookUseCase
import com.raka.books.usecase.GetBookUseCaseImpl
import com.raka.books.usecase.GetBooksUseCase
import com.raka.books.usecase.GetBooksUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/**
 * Dagger providers for UseCase components
 */
@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    /**
     * provides GetBookUseCase
     * @param impl of GetBookUseCaseImpl type
     * @return GetBookUseCase instance
     */
    @Binds
    fun provideGetBookUseCase(impl: GetBookUseCaseImpl)
            : GetBookUseCase

    /**
     * provides GetBooksUseCase
     * @param impl of GetBooksUseCaseImpl type
     * @return GetBooksUseCase instance
     */
    @Binds
    fun provideGetBooksUseCase(impl: GetBooksUseCaseImpl)
            : GetBooksUseCase
}