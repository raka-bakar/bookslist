package com.raka.data.datasource

import android.os.Build
import com.raka.data.model.Book
import com.raka.data.model.ResponseItem
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

/**
 * A helper class for data source
 */
interface DataSourceHelper {
    /**
     * Helper method that maps ResponseItem to Book
     * @param listResponseItem of type ResponseItem
     * @return List of Book
     */
    fun mapBookResponseToBook(listResponseItem: List<ResponseItem?>): List<Book>

    /**
     * format date from x/x/xxxx to Wed, Jul, '20
     * @param date type String
     * @return New date format type string
     */
    fun formatDetailDate(date: String): String

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

    override fun mapBookResponseToBook(listResponseItem: List<ResponseItem?>): List<Book> {
        val newList: MutableList<Book> = mutableListOf()
        listResponseItem.sortedByDescending { formatItemDate(it?.releaseDate) }.forEach { item ->
            item?.let { responseItem ->
                val title =
                    if (responseItem.titlee?.isNotEmpty() == true) responseItem.titlee else responseItem.title
                val id = checkId(responseItem.id)
                newList.add(
                    Book(
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

    override fun checkId(id: Int?): Int {
        if (id == null) return 0

        if (id < 0) return id * -1

        return id
    }

    override fun formatItemDate(date: String?): Long {
        val yearFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)
        val invalidDate = yearFormat.parse("-9999")?.time ?: 0
        if (date == null ){
            return invalidDate
        }

        try {
            val formatter = if (date.length == 4) {
                yearFormat
            } else {
                SimpleDateFormat("M/dd/yyyy", Locale.ENGLISH)
            }
            return date.let { formatter.parse(it)?.time } ?: invalidDate
        } catch (e: ParseException) {
            // if format is unknown, will return the invalid date and put in the bottom of the list
            Timber.e(e)
            return invalidDate
        }
    }

    override fun formatDetailDate(date: String): String {
        return try {
            val inputFormat = SimpleDateFormat("M/dd/yyyy", Locale.US)
            val outputFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                SimpleDateFormat("EEE, MMM d, ''YY", Locale.US)
            } else {
                SimpleDateFormat("EEE, MMM d, ''yy", Locale.US)
            }
            val newDate = date.let { inputFormat.parse(it) }
            newDate?.let { outputFormat.format(it) } ?: date
        } catch (e: ParseException) {
            // if format is unknown, will return the original date
            Timber.e(e)
            date
        }
    }
}