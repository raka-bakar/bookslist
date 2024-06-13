package com.raka.books.usecase

import com.raka.books.repository.BookRepository
import com.raka.data.CallResult
import com.raka.data.model.BookItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Usecase class to provide a list of books
 */
interface GetBooksUseCase {
    /**
     * get a list of BookItem
     * @return Flow of CallResult of list of BookItem type
     */
    fun getBooks(): Flow<CallResult<List<BookItem>>>
}

class GetBooksUseCaseImpl @Inject constructor(private val bookRepository: BookRepository) :
    GetBooksUseCase {
    override fun getBooks(): Flow<CallResult<List<BookItem>>> {
        return bookRepository.getBooks()
    }
}