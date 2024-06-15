package com.raka.data.datasource

import android.os.Build
import com.raka.data.database.DBBook
import com.raka.data.model.BookDetail
import com.raka.data.model.BookItem
import com.raka.data.model.ResponseItem
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

/**
 * A helper class for data source
 */
internal interface DataSourceHelper {
    /**
     * Helper method that maps BooksResponse to DBBook
     * @param listResponseItem of type BookResponse
     * @return List of DBBook
     */
    fun mapBookResponseToDBBook(listResponseItem: List<ResponseItem?>): List<DBBook>

    /**
     * Helper method that maps DBBook to BookDetail
     * @param dbBook of type DBBook
     * @return List of BookDetail
     */
    fun mapDbBookToBookDetail(dbBook: DBBook): BookDetail

    /**
     * Helper method that maps bookResponse to BookItem
     * @param listResponseItem of type BookResponse
     * @return List of BookItem
     */
    fun mapBookResponseToBookItem(listResponseItem: List<ResponseItem?>): List<BookItem>

    /**
     * format date from x/x/xxxx to Wed, Jul, '20
     * @param date type String
     * @return New date format type string
     */
    fun formatSortDate(date: String): String

    /**
     * check if ID of books is minus or null
     * if minus, will be converted to positive
     * if null will return 0
     * @param id of Book
     * @return Id of book type Int
     */
    fun checkId(id: Int?): Int

    /**
     * sort release date by converting it to SimpleDate type first
     * then return Long value
     * For now it only handle 2 formats (YYYY) and (M/dd/yyyy)
     * Other than that will return the oldest date/ put at the bottom of the list
     * @param date of type String
     * @return Date type of Long
     */
    fun formatItemDate(date: String?): Long
}

internal class DataSourceHelperImpl @Inject constructor() : DataSourceHelper {

    override fun mapBookResponseToDBBook(listResponseItem: List<ResponseItem?>): List<DBBook> {
        val newList: MutableList<DBBook> = mutableListOf()
        listResponseItem.sortedByDescending { formatItemDate(it?.releaseDate) }.forEach { item ->
            item?.let { responseItem ->
                val title =
                    if (responseItem.titlee?.isNotEmpty() == true) responseItem.titlee else responseItem.title
                val id = checkId(responseItem.id)
                newList.add(
                    DBBook(
                        id = id,
                        author = responseItem.author ?: "",
                        image = responseItem.image ?: "",
                        releaseDate = responseItem.releaseDate ?: "",
                        description = responseItem.description ?: "",
                        title = title ?: ""
                    )
                )
            }
        }
        return newList
    }

    override fun mapDbBookToBookDetail(dbBook: DBBook): BookDetail {
        return BookDetail(
            id = dbBook.id,
            author = dbBook.author,
            description = dbBook.description,
            image = dbBook.image,
            releaseDate = formatSortDate(dbBook.releaseDate),
            title = dbBook.title
        )
    }

    override fun mapBookResponseToBookItem(listResponseItem: List<ResponseItem?>): List<BookItem> {
        val newList: MutableList<BookItem> = mutableListOf()
        listResponseItem.sortedByDescending { formatItemDate(it?.releaseDate) }.forEach { item ->
            item?.let { responseItem ->
                val title =
                    if (responseItem.titlee?.isNotEmpty() == true) responseItem.titlee else responseItem.title
                val id = checkId(responseItem.id)
                newList.add(
                    BookItem(
                        id = id,
                        title = title ?: "",
                        description = responseItem.description ?: "",
                        image = responseItem.image ?: "",
                        releaseDate = responseItem.releaseDate ?: ""
                    )
                )
            }
        }
        return newList
    }

    override fun checkId(id: Int?): Int {
        if (id == null) return 0

        if (id < 0) return id * -1

        return id
    }

    override fun formatItemDate(date: String?): Long {
        try {
            val formatter = if (date?.length == 4) {
                SimpleDateFormat("yyyy", Locale.ENGLISH)
            } else {
                SimpleDateFormat("M/dd/yyyy", Locale.ENGLISH)
            }
            return date?.let { formatter.parse(it)?.time } ?: 0
        } catch (e: ParseException) {
            // if format is unknown, will return the oldest date/ send to the bottom of the list
            Timber.e(e)
            val format = SimpleDateFormat("yyyy", Locale.ENGLISH)
            val result = format.parse("-9999")?.time ?: 0
            return result
        }
    }

    override fun formatSortDate(date: String): String {
        return try {
            val inputFormat = SimpleDateFormat("M/dd/yyyy", Locale.US)
            val outputFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                SimpleDateFormat("EEE, MMM d, ''YY", Locale.US)
            } else {
                SimpleDateFormat("EEE, MMM d, ''yy", Locale.US)
            }
            val newDate = date.let { inputFormat.parse(it) }
            newDate?.let { outputFormat.format(it) } ?: ""
        } catch (e: ParseException) {
            // if format is unknown, will return the original date
            Timber.e(e)
            date
        }
    }
}