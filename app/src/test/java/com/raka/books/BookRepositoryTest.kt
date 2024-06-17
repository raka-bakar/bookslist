package com.raka.books

import com.raka.books.repository.BookRepository
import com.raka.books.repository.BookRepositoryImpl
import com.raka.data.CallResult
import com.raka.data.DataProvider
import com.raka.data.model.Book
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify

class BookRepositoryTest {

    private val dataProvider = Mockito.mock(DataProvider::class.java)

    private lateinit var sut: BookRepository

    @Before
    fun setup() {
        sut = BookRepositoryImpl(dataProvider)
    }

    @Test
    fun `verify pass the correct book Id when getting a detail information of book from the database`() =
        runBlocking {
            val expected = CallResult.success(DataTest.bookDetailTest)
            val bookId = 1
            Mockito.`when`(
                dataProvider.loadBook(bookId)
            ).thenReturn(flow { emit(expected) })

            val result = sut.getBook(bookId)

            result.collect {
                verify(dataProvider).loadBook(bookId)
            }
        }

    @Test
    fun `verify the correct book data when getting a detail information of book from the database`() =
        runBlocking {
            val expected = CallResult.success(DataTest.bookDetailTest)
            val bookId = 1
            Mockito.`when`(
                dataProvider.loadBook(bookId)
            ).thenReturn(flow { emit(expected) })

            val result = sut.getBook(bookId)

            result.collect {
                Assert.assertEquals(expected, it)
                Assert.assertEquals(DataTest.bookDetailTest, it.data)
            }
        }

    @Test
    fun `return callResult error when getting detail book information fails`() =
        runBlocking {
            val expected = CallResult.error<Book>("error message")
            val bookId = 1
            Mockito.`when`(
                dataProvider.loadBook(bookId)
            ).thenReturn(flow { emit(expected) })

            val result = sut.getBook(bookId)

            result.collect {
                Assert.assertEquals(expected, it)
                Assert.assertEquals(null, it.data)
            }
        }

    @Test
    fun `verify return the correct data when getting a list of books`() =
        runBlocking {
            val expectedData = listOf(DataTest.bookTest1, DataTest.bookTest2)
            val expected = CallResult.success(expectedData)
            Mockito.`when`(
                dataProvider.loadBooks()
            ).thenReturn(flow { emit(expected) })

            val result = sut.getBooks()

            result.collect {
                Assert.assertEquals(expected, it)
                Assert.assertEquals(expectedData, it.data)
            }
        }

    @Test
    fun `return callResult error when getting a list of books`() =
        runBlocking {
            val expected = CallResult.error<List<Book>>("error message")
            Mockito.`when`(
                dataProvider.loadBooks()
            ).thenReturn(flow { emit(expected) })

            val result = sut.getBooks()

            result.collect {
                Assert.assertEquals(expected, it)
                Assert.assertEquals(null, it.data)
            }
        }
}