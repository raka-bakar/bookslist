package com.raka.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.raka.data.model.BookItem
import kotlinx.coroutines.flow.Flow

@Dao
internal interface BookDao {

    /**
     * insert list of DBBook
     *  @param list  list of DBBook
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(list: List<DBBook>)

    /**
     * load all books from table book
     *  @return flow type of List<DBBook>
     */
    @Transaction
    @Query("SELECT id, title,description,image FROM book")
    fun loadBooks(): List<BookItem>

    /**
     * load a book from table book
     * @param id of book
     *  @return flow type of DBBook
     */
    @Transaction
    @Query("SELECT * FROM book WHERE id = :id")
    fun loadBook(id: Int): DBBook
}