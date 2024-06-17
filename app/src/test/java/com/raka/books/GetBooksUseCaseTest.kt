package com.raka.books

import com.raka.books.repository.BookRepository
import com.raka.books.usecase.GetBooksUseCase
import com.raka.books.usecase.GetBooksUseCaseImpl
import com.raka.data.CallResult
import com.raka.data.model.Book
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetBooksUseCaseTest {
    private val repository = Mockito.mock(BookRepository::class.java)

    private lateinit var sut: GetBooksUseCase

    @Before
    fun setup() {
        sut = GetBooksUseCaseImpl(repository)
    }

    @Test
    fun `verify return the correct data when getting a list of books`() =
        runBlocking {
            val expectedData = listOf(DataTest.bookTest1, DataTest.bookTest2)
            val expected = CallResult.success(expectedData)
            Mockito.`when`(
                repository.getBooks()
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
                repository.getBooks()
            ).thenReturn(flow { emit(expected) })

            val result = sut.getBooks()

            result.collect {
                Assert.assertEquals(expected, it)
                Assert.assertEquals(null, it.data)
            }
        }
}