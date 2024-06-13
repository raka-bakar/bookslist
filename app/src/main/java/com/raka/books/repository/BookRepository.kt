package com.raka.books.repository

import com.raka.data.CallResult
import com.raka.data.DataProvider
import com.raka.data.model.BookDetail
import com.raka.data.model.BookItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository to provide Book data
 */
interface BookRepository {

    /**
     * get a list of BookItem
     * @return Flow of CallResult of list of BookItem type
     */
    fun getBooks(): Flow<CallResult<List<BookItem>>>

    /**
     * get a BookDetail
     * @return Flow of CallResult of BookDetail
     */
    fun getBook(id: Int): Flow<CallResult<BookDetail>>
}

class BookRepositoryImpl @Inject constructor(private val dataProvider: DataProvider) :
    BookRepository {
    override fun getBooks(): Flow<CallResult<List<BookItem>>> {
        return dataProvider.loadBooks()
    }

    override fun getBook(id: Int): Flow<CallResult<BookDetail>> {
        return dataProvider.loadBook(id)
    }
}