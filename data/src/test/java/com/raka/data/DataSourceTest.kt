package com.raka.data

import com.raka.data.api.ApiService
import com.raka.data.database.BookDao
import com.raka.data.datasource.DataSource
import com.raka.data.datasource.DataSourceHelper
import com.raka.data.datasource.DataSourceImpl
import com.raka.data.model.Book
import com.raka.data.model.ResponseItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify
import retrofit2.Response

class DataSourceTest {

    val apiService = Mockito.mock(ApiService::class.java)
    val dao = Mockito.mock(BookDao::class.java)
    val helper = Mockito.mock(DataSourceHelper::class.java)
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

    private lateinit var sut: DataSource

    @Before
    fun setup() {
        sut = DataSourceImpl(apiService = apiService, bookDao = dao, helper = helper)
    }

    @Test
    fun `verify the correct book id is sent when getting a book data`(): Unit = runBlocking {
        val idBook = 1
        Mockito.`when`(dao.loadBook(idBook)).thenReturn(bookTest1)
        Mockito.`when`(helper.formatDetailDate(bookTest1.releaseDate)).thenReturn("Tue, Jan 2, '23")

        val result = sut.loadBook(idBook)
        result.collectLatest {
            verify(dao).loadBook(idBook)
        }
    }

    @Test
    fun `verify the correct release date is sent when getting a book data`(): Unit = runBlocking {
        val idBook = 1
        val initialDate = "1/2/1923"
        Mockito.`when`(dao.loadBook(idBook)).thenReturn(bookTest1)
        Mockito.`when`(helper.formatDetailDate(bookTest1.releaseDate)).thenReturn("Tue, Jan 2, '23")

        val result = sut.loadBook(idBook)
        result.collectLatest {
            verify(helper).formatDetailDate(initialDate)
        }
    }

    @Test
    fun `return the correct book data`() = runBlocking {
        val expected = Book(
            id = 1, image = "https:image.url", releaseDate = "Tue, Jan 2, '23",
            author = "J.K. Rowling", description = "Book description", title = "Harry Potter"
        )
        Mockito.`when`(dao.loadBook(1)).thenReturn(bookTest1)
        Mockito.`when`(helper.formatDetailDate(bookTest1.releaseDate)).thenReturn("Tue, Jan 2, '23")

        val result = sut.loadBook(1)
        result.collectLatest {
            Assert.assertEquals(expected, it.data)
        }
    }

    @Test
    fun `when response is success, return the correct list of book data`() = runBlocking {
        val responseList = listOf(responseItemTest1)
        val expected = listOf(bookTest1)
        val response = Response.success(listOf(responseItemTest1))
        Mockito.`when`(apiService.loadBooks()).thenReturn(response)
        Mockito.`when`(helper.mapBookResponseToBook(responseList)).thenReturn(expected)

        val result = sut.loadBooks()
        result.collectLatest {
            Assert.assertEquals(expected, it.data)
        }
    }

    @Test
    fun `when response is not success, return the call state error and data is null`() =
        runBlocking {
            val expected =
                CallResult.error<List<ResponseItem>>("Network error, please check your Internet connection")

            val errorResponse =
                "{\n" +
                        "  \"type\": \"error\",\n" +
                        "  \"message\": \"What you were looking for isn't here.\"\n" + "}"
            val errorResponseBody =
                errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
            val response = Response.error<List<ResponseItem>>(500, errorResponseBody)
            Mockito.`when`(apiService.loadBooks()).thenReturn(response)

            val result = sut.loadBooks()
            result.collectLatest {
                Assert.assertEquals(expected, it)
                Assert.assertEquals(null, it.data)
            }
        }

    @Test
    fun `when response is not success,load from local database`() =
        runBlocking {
            val expected =
                CallResult.success(listOf(bookTest1, bookTest2))

            val errorResponse =
                "{\n" +
                        "  \"type\": \"error\",\n" +
                        "  \"message\": \"What you were looking for isn't here.\"\n" + "}"
            val errorResponseBody =
                errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
            val response = Response.error<List<ResponseItem>>(500, errorResponseBody)
            Mockito.`when`(apiService.loadBooks()).thenReturn(response)
            Mockito.`when`(dao.loadBooks()).thenReturn(listOf(bookTest1, bookTest2))

            val result = sut.loadBooks()
            result.collectLatest {
                Assert.assertEquals(expected, it)
            }
        }
}