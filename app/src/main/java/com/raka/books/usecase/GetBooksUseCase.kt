package com.raka.books.usecase

import com.raka.books.repository.BookRepository
import com.raka.data.CallResult
import com.raka.data.model.Book
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Usecase class to provide a list of books
 */
interface GetBooksUseCase {
    /**
     * get a list of Book
     * @return Flow of CallResult of list of Book type
     */
    fun getBooks(): Flow<CallResult<List<Book>>>
}

class GetBooksUseCaseImpl @Inject constructor(private val bookRepository: BookRepository) :
    GetBooksUseCase {
    override fun getBooks(): Flow<CallResult<List<Book>>> {
        return bookRepository.getBooks()
    }
}