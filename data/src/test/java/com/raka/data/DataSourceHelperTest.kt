package com.raka.data

import com.raka.data.datasource.DataSourceHelper
import com.raka.data.datasource.DataSourceHelperImpl
import com.raka.data.model.Book
import com.raka.data.model.ResponseItem
import org.junit.Assert
import org.junit.Test

internal class DataSourceHelperTest {
    private var sut: DataSourceHelper = DataSourceHelperImpl()
    private val bookTest1 = Book(
        id = 1, image = "https:image.url", releaseDate = "1/2/1923",
        author = "J.K. Rowling", description = "Book description", title = "Harry Potter"
    )
    private val bookTest2 = Book(
        id = 2, image = "https:image.url", releaseDate = "5/12/1889",
        author = "Maribelle", description = "Book description", title = "Blood on the white snow"
    )

    private val responseItemTest1 = ResponseItem(
        id = 1, image = "https:image.url", releaseDate = "1/2/1923",
        author = "J.K. Rowling", description = "Book description", title = "Harry Potter"
    )

    private val responseItemTest2 = ResponseItem(
        id = 2, image = "https:image.url", releaseDate = "5/12/1889",
        author = "Maribelle", description = "Book description", title = "Blood on the white snow"
    )

    private val invalidDate= -377711773200000L

    @Test
    fun `mapping ResponseItem to Book return the correct data`() {
        val expected = mutableListOf(bookTest1)

        val testData = mutableListOf(responseItemTest1)
        val result = sut.mapBookResponseToBook(testData)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `mapping ResponseItem to DBBook return the correct sorted list by descending`() {
        val expected = mutableListOf(bookTest1, bookTest2)

        val testData = mutableListOf(responseItemTest2, responseItemTest1)

        val result = sut.mapBookResponseToBook(testData)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `return emptyList when List of ResponseItem is empty`() {
        val expected: MutableList<Book> = mutableListOf()

        val result = sut.mapBookResponseToBook(emptyList())
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `return emptyList when a list of Response Item contain null`() {
        val expected: MutableList<Book> = mutableListOf()
        val testData: MutableList<ResponseItem?> = mutableListOf(null)

        val result = sut.mapBookResponseToBook(testData)

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `return the correct list when one of the items in the list is null`() {
        val expected: MutableList<Book> = mutableListOf(bookTest1)
        val testData: MutableList<ResponseItem?> = mutableListOf(null, responseItemTest1)

        val result = sut.mapBookResponseToBook(testData)

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `return the correct id when inserting a minus value of id`() {
        val expected = 2

        val result = sut.checkId(-2)

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `return 0 when inserted id is null`() {
        val expected = 0

        val result = sut.checkId(null)

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `return the correct long value of date type when inserting date format Mddyyy Date`() {
        // format date M/dd/yyyy
        val expected = 221871600000L

        val result = sut.formatItemDate("1/12/1977")

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `return the correct long value of date type when inserting date format yyyy Date`() {
        // format date yyyy
        val expected = 220921200000L

        val result = sut.formatItemDate("1977")

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `return invalidDate when inserting null type`() {
        val expected = invalidDate

        val result = sut.formatItemDate(null)

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `return invalidDate when inserting unknown date format`() {
        val expected = invalidDate

        val result = sut.formatItemDate("800 BC")

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `return the correct date format when formatting date detail`() {
        val expected = "Tue, Jan 2, '23"

        val result = sut.formatDetailDate("1/2/1923")

        Assert.assertEquals(expected, result)
    }
    @Test
    fun `return the same inputted date when date format is unknown`() {
        val expected = "800 BC"

        val result = sut.formatDetailDate("800 BC")

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `return empty string when inputted date is empty`() {
        val expected = ""

        val result = sut.formatDetailDate("")

        Assert.assertEquals(expected, result)
    }
}