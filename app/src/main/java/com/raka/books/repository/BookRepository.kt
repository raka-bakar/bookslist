package com.raka.books.repository

import com.raka.data.CallResult
import com.raka.data.DataProvider
import com.raka.data.model.Book
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository to provide Book data
 */
interface BookRepository {

    /**
     * get a list of Book
     * @return Flow of CallResult of list of Book type
     */
    fun getBooks(): Flow<CallResult<List<Book>>>

    /**
     * get a Book
     * @return Flow of CallResult of Book
     */
    fun getBook(id: Int): Flow<CallResult<Book>>
}

class BookRepositoryImpl @Inject constructor(private val dataProvider: DataProvider) :
    BookRepository {
    override fun getBooks(): Flow<CallResult<List<Book>>> {
        return dataProvider.loadBooks()
    }

    override fun getBook(id: Int): Flow<CallResult<Book>> {
        return dataProvider.loadBook(id)
    }
}