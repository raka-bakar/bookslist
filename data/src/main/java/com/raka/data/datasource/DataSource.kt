package com.raka.data.datasource

import com.raka.data.CallResult
import com.raka.data.api.ApiService
import com.raka.data.database.BookDao
import com.raka.data.database.DBBook
import com.raka.data.model.BookDetail
import com.raka.data.model.BookItem
import com.raka.data.model.ResponseItem
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
            val responseCall = async(Dispatchers.IO) {
                ApiService.apiCall { apiService.loadBooks() }
            }
            val response = responseCall.await()
            if (response.isSuccess() && response.data != null) {
                // when network call success
                val data: List<ResponseItem> = response.data
                val dbBooks: List<DBBook> = helper.mapBookResponseToDBBook(data)
                // save dbBooks to local database
                launch(Dispatchers.IO) { bookDao.insertBooks(dbBooks) }
                val listBookItem: List<BookItem> =
                    helper.mapBookResponseToBookItem(data)

                emit(CallResult.success(listBookItem))
            } else if (response.isFail()) {
                // when network call fails, load from local database
                val localData = bookDao.loadBooks()

                if (localData.isEmpty()) {
                    emit(CallResult.error("Network error, please check your Internet connection"))
                    Timber.e(response.message)
                } else {
                    emit(CallResult.success(localData.sortedByDescending { helper.formatItemDate(it.releaseDate) }))
                }
            }
        }
    }
}