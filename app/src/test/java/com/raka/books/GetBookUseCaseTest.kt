package com.raka.books

import com.raka.books.repository.BookRepository
import com.raka.books.usecase.GetBookUseCase
import com.raka.books.usecase.GetBookUseCaseImpl
import com.raka.data.CallResult
import com.raka.data.model.Book
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify

class GetBookUseCaseTest {
    private val repository = Mockito.mock(BookRepository::class.java)

    private lateinit var sut: GetBookUseCase

    @Before
    fun setup() {
        sut = GetBookUseCaseImpl(repository)
    }

    @Test
    fun `verify pass the correct book Id when getting a detail information of book from the database`() =
        runBlocking {
            val expected = CallResult.success(DataTest.bookDetailTest)
            val bookId = 1
            Mockito.`when`(
                repository.getBook(bookId)
            ).thenReturn(flow { emit(expected) })

            val result = sut.getBook(bookId)

            result.collect {
                verify(repository).getBook(bookId)
            }
        }

    @Test
    fun `verify the correct book data when getting a detail information of book from the database`() =
        runBlocking {
            val expected = CallResult.success(DataTest.bookDetailTest)
            val bookId = 1
            Mockito.`when`(
                repository.getBook(bookId)
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
                repository.getBook(bookId)
            ).thenReturn(flow { emit(expected) })

            val result = sut.getBook(bookId)

            result.collect {
                Assert.assertEquals(expected, it)
                Assert.assertEquals(null, it.data)
            }
        }
}