package com.raka.books

import com.raka.data.model.Book

internal object DataTest {
    val bookTest1 = Book(
        id = 1, image = "https:image.url", releaseDate = "5/10/1890",
        author = "J.K. Rowling", description = "Book description", title = "Harry Potter"
    )

    val bookTest2 = Book(
        id = 2, image = "https:image.url", releaseDate = "5/12/1889",
        author = "Maribelle", description = "Book description", title = "Blood on the white snow"
    )

    val bookDetailTest = Book(
        id = 1, image = "https:image.url", releaseDate = "Mon, Oct 19, '53",
        author = "J.K. Rowling", description = "Book description", title = "Harry Potter"
    )
}