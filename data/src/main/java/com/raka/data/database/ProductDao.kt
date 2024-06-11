package com.raka.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
internal interface ProductDao {

    /**
     * insert list of DBProduct
     *  @param list  list of DBProduct
     *  @return Completable type
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProducts(list: List<DBProduct>): Completable

    /**
     * load all products from table product
     *  @return Single type of List<DBProduct>
     */
    @Transaction
    @Query("SELECT * FROM product")
    fun loadProducts(): Single<List<DBProduct>>

    /**
     * load all products from table product filtered by category
     *  @return Single type of List<DBProduct>
     */
    @Transaction
    @Query("SELECT * FROM product WHERE category = :category")
    fun loadProductsByCategory(category: String): Single<List<DBProduct>>

    /**
     * load all favorite products from table produc
     *  @return Single type of List<DBProduct>
     */
    @Transaction
    @Query("SELECT * FROM product WHERE isFavorite = :isFavorite")
    fun loadFavoriteProducts(isFavorite: Boolean): Single<List<DBProduct>>

    /**
     * load a product from table product
     * @param id of product
     *  @return Single type of DBProduct
     */
    @Transaction
    @Query("SELECT * FROM product WHERE id = :id")
    fun loadProduct(id: Int): Single<DBProduct>

    /**
     * Update the bookmark value of a product
     *  @param list  list of DBProduct
     *  @return Completable type
     */
    @Transaction
    @Query("UPDATE product SET isFavorite = :status WHERE id = :id")
    fun updateProduct(id: Int, status: Boolean): Completable
}