package com.raka.books.usecase

import com.raka.books.repository.BookRepository
import com.raka.data.CallResult
import com.raka.data.model.BookDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Usecase class to provide a book data
 */
interface GetBookUseCase {
    /**
     * Get book detail by id
     * @param id of the book, type Int
     * @return Flow of CallResult of Book detail type
     */
    fun getBook(id: Int): Flow<CallResult<BookDetail>>
}

class GetBookUseCaseImpl @Inject constructor(private val bookRepository: BookRepository) :
    GetBookUseCase {
    override fun getBook(id: Int): Flow<CallResult<BookDetail>> {
        return bookRepository.getBook(id)
    }
}