package com.raka.data.datasource

import com.raka.data.database.DBBook
import com.raka.data.model.BookDetail
import com.raka.data.model.BookItem
import com.raka.data.model.BookResponse
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
     * @param bookResponse of type BookResponse
     * @return List of DBBook
     */
    fun mapBookResponseToDBBook(bookResponse: BookResponse): DBBook

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
     * @param bookResponse of type BookResponse
     * @return List of BookItem
     */
    fun mapBookResponseToBookItem(bookResponse: BookResponse): BookItem

    /**
     * format date from x/x/xxxx to Wed, Jul, '20
     * @param date type String
     * @return New date format type string
     */
    fun formatDate(date: String): String
}

internal class DataSourceHelperImpl @Inject constructor() : DataSourceHelper {
    override fun mapBookResponseToDBBook(bookResponse: BookResponse): DBBook {
        val title =
            if (bookResponse.titlee?.isNotEmpty() == true) bookResponse.titlee else bookResponse.title
        return (
                DBBook(
                    id = bookResponse.id ?: 0,
                    author = bookResponse.author ?: "",
                    image = bookResponse.image ?: "",
                    releaseDate = bookResponse.releaseDate ?: "",
                    description = bookResponse.description ?: "",
                    title = title ?: ""
                )
                )
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

    override fun mapBookResponseToBookItem(bookResponse: BookResponse): BookItem {
        val title =
            if (bookResponse.titlee?.isNotEmpty() == true) bookResponse.titlee else bookResponse.title
        return BookItem(
            id = bookResponse.id ?: 0,
            title = title ?: "",
            description = bookResponse.description ?: "",
            image = bookResponse.image ?: ""
        )
    }

    override fun formatDate(date: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val datee = date.let { inputFormat.parse(it) }
            datee?.let { outputFormat.format(it) }?:""
        } catch (e: ParseException) {
            Timber.e(e)
            date
        }
    }
}