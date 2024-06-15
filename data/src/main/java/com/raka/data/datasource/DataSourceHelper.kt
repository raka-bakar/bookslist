package com.raka.data.datasource

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
     * Helper method that maps DBBook to BookItem
     * @param dbBook of type DBBook
     * @return List of BookItem
     */
    fun mapDbBookToBookItem(dbBook: DBBook): BookItem

    /**
     * Helper method that maps DBBook to BookDetail
     * @param dbBook of type DBBook
     * @return List of BookDetail
     */
    fun mapDbBookToBookDetail(dbBook: DBBook): BookDetail

    /**
     * Helper method that maps bookResponse to BookItem
     * @param responseItem of type BookResponse
     * @return List of BookItem
     */
    fun mapBookResponseToBookItem(listResponseItem: List<ResponseItem?>): List<BookItem>

//    /**
//     * Helper method that maps bookResponse to BookItem
//     * @param responseItem of type BookResponse
//     * @return List of BookItem
//     */
//    fun mapBookItemToDBBook(listBookItem: List<BookItem>): List<DBBook>

    /**
     * format date from x/x/xxxx to Wed, Jul, '20
     * @param date type String
     * @return New date format type string
     */
    fun formatDate(date: String): String
}

internal class DataSourceHelperImpl @Inject constructor() : DataSourceHelper {
    override fun mapBookResponseToDBBook(listResponseItem: List<ResponseItem?>): List<DBBook> {
        val newList: MutableList<DBBook> = mutableListOf()
        listResponseItem.sortedBy { it?.releaseDate }.forEach { item ->
            item?.let { responseItem ->
                val title =
                    if (responseItem.titlee?.isNotEmpty() == true) responseItem.titlee else responseItem.title
                newList.add(
                    DBBook(
                        id = responseItem.id ?: 0,
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

    override fun mapDbBookToBookItem(dbBook: DBBook): BookItem {
        return BookItem(
            id = dbBook.id,
            title = dbBook.title,
            description = dbBook.description,
            image = dbBook.image
        )
    }

    override fun mapDbBookToBookDetail(dbBook: DBBook): BookDetail {
        return BookDetail(
            id = dbBook.id,
            author = dbBook.author,
            description = dbBook.description,
            image = dbBook.image,
            releaseDate = formatDate(dbBook.releaseDate),
            title = dbBook.title
        )
    }

    override fun mapBookResponseToBookItem(listResponseItem: List<ResponseItem?>): List<BookItem> {
        val newList: MutableList<BookItem> = mutableListOf()
        listResponseItem.sortedBy { it?.releaseDate }.forEach { item ->
            item?.let { responseItem ->
                val title =
                    if (responseItem.titlee?.isNotEmpty() == true) responseItem.titlee else responseItem.title
                newList.add(
                    BookItem(
                        id = responseItem.id ?: 0,
                        title = title ?: "",
                        description = responseItem.description ?: "",
                        image = responseItem.image ?: ""
                    )
                )
            }
        }
        return newList
    }

    override fun formatDate(date: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val datee = date.let { inputFormat.parse(it) }
            datee?.let { outputFormat.format(it) } ?: ""
        } catch (e: ParseException) {
            Timber.e(e)
            date
        }
    }
}