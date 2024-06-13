package com.raka.data.datasource

import com.raka.data.CallResult
import com.raka.data.api.ApiService
import com.raka.data.database.BookDao
import com.raka.data.model.BookDetail
import com.raka.data.model.BookItem
import com.raka.data.model.BookResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Data source class that provides data from remote server or local database
 */
internal interface DataSource {

    /**
     * get a detail information of a book,
     * @param id a Book
     * @return Flow of DBBook
     */
    fun loadBook(id: Int): Flow<CallResult<BookDetail>>

    /**
     * load initial data of Books from remote server and save it into the database
     * then display the list of books
     * @return Completable
     */
    fun loadBooks(): Flow<CallResult<List<BookItem>>>
}

internal class DataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val bookDao: BookDao,
    private val helper: DataSourceHelper
) : DataSource {

    override fun loadBook(id: Int): Flow<CallResult<BookDetail>> = flow {
        val dbBook = bookDao.loadBook(id = id)
        val bookDetail = helper.mapDbBookToBookDetail(dbBook)
        emit(CallResult.success(bookDetail))
    }

    override fun loadBooks(): Flow<CallResult<List<BookItem>>> = flow {
        // load data from remote server and save to the local database
        coroutineScope {
            val responseCall = async { apiService.loadBooks() }
            val response = responseCall.await()
            val listBookResponse: List<BookResponse>? = response.body()
            if (response.isSuccessful && listBookResponse != null) {
                // when network call success
                val dbBooks = listBookResponse.map { bookResponse ->
                    helper.mapBookResponseToDBBook(bookResponse)
                }
                // save dbBooks to local database
                launch { bookDao.insertBooks(dbBooks) }
                val listBookItem = listBookResponse.map { bookReponse ->
                    helper.mapBookResponseToBookItem(bookReponse)
                }
                emit(CallResult.success(listBookItem))
            } else {
                // when network call fails, load from local database
                val localData = bookDao.loadBooks()
                if (localData.isEmpty()) {
                    emit(CallResult.error("No data found"))
                    Timber.e(response.message())
                } else {
                    val listBookItem =
                        localData.map { dbBook -> helper.mapDbBookToBookItem(dbBook) }
                    emit(CallResult.success(listBookItem))
                }
            }
        }
    }
}