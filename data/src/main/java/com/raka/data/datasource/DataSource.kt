package com.raka.data.datasource

import android.util.Log
import com.raka.data.CallResult
import com.raka.data.api.ApiService
import com.raka.data.database.BookDao
import com.raka.data.model.Book
import com.raka.data.model.ResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.internal.toHeaderList
import retrofit2.Response
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
    fun loadBook(id: Int): Flow<CallResult<Book>>

    /**
     * load initial data of Books from remote server and save it into the database
     * then display the list of books
     * @return Completable
     */
    fun loadBooks(): Flow<CallResult<List<Book>>>
}

internal class DataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val bookDao: BookDao,
    private val helper: DataSourceHelper
) : DataSource {

    override fun loadBook(id: Int): Flow<CallResult<Book>> = flow {
        val dbBook = bookDao.loadBook(id = id)
        dbBook.releaseDate = helper.formatDetailDate(dbBook.releaseDate)
        emit(CallResult.success(dbBook))
    }

    override fun loadBooks(): Flow<CallResult<List<Book>>> = flow {
        // load data from remote server and save to the local database
        coroutineScope {
            val errorResult =
                CallResult.error<List<Book>>("Network error, please check your Internet connection")
            try {
                val responseCall = apiService.loadBooks()
                val response = convertResponse(responseCall)
                if (response.isSuccess() && response.data != null) {
                    // when network call success
                    val data: List<ResponseItem> = response.data
                    val listBook: List<Book> = helper.mapBookResponseToBook(data)

                    // save dbBooks to local database
                    launch{ bookDao.insertBooks(listBook) }

                    emit(CallResult.success(listBook))
                } else if (response.isFail()) {
                    // when network call fails, load from local database
                   loadFromLocalDatabase(errorResult)
                }
            } catch (e: Exception) {
                Timber.e(e)
                emit(errorResult)
            }
        }
    }

    private fun loadFromLocalDatabase(errorResult: CallResult<List<Book>>): Flow<CallResult<List<Book>>> = flow {
        val localData = bookDao.loadBooks()

        if (localData.isEmpty()) {
            emit(errorResult)
        } else {
            emit(CallResult.success(localData.sortedByDescending {
                helper.formatItemDate(
                    it.releaseDate
                )
            }))
        }
    }

    private fun convertResponse(response: Response<List<ResponseItem>>): CallResult<List<ResponseItem>> {
        val body = response.body()
        if (response.isSuccessful) {
            val headers = response.headers().toHeaderList().associate {
                it.name.utf8() to it.value.utf8()
            }
            return CallResult.success(body, headers, "", response.code())
        }
        return CallResult.error(response.message(), response.code(), null)
    }
}