package com.raka.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.raka.data.model.Book

@Dao
internal interface BookDao {

    /**
     * insert list of Book
     *  @param list  list of Book
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(list: List<Book>)

    /**
     * load all books from table book
     *  @return flow type of List<Book>
     */
    @Transaction
    @Query("SELECT * FROM book")
    fun loadBooks(): List<Book>

    /**
     * load a book from table book
     * @param id of book
     *  @return flow type of Book
     */
    @Transaction
    @Query("SELECT * FROM book WHERE id = :id")
    fun loadBook(id: Int): Book
}