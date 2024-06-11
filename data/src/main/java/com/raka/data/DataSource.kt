package com.raka.data

import com.raka.data.api.ApiService
import com.raka.data.database.DBProduct
import com.raka.data.database.ProductDao
import com.raka.data.utils.toDBProduct
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import javax.inject.Inject

internal interface DataSource {

    /**
     * get a detail information of a product,
     * @param id a Product
     * @return Single of DBProduct
     */
    fun loadProduct(id: Int): Single<DBProduct>

    /**
     * update favorite status of a product
     * @param id  of Product
     * @param status  favorite status of a product
     * @return Completable type
     */
    fun updateFavoriteStatus(id: Int, status: Boolean): Completable

    /**
     * load initial data from remote server and save it into the database
     * @return Completable
     */
    fun loadInitialData(): Completable

    /**
     * get list of DBProduct from database
     * @return Single of ProductResponse
     */
    fun loadProductsLocalStorage(): Single<List<DBProduct>>

    /**
     * get a list of products by category
     * @param id  of Product
     * @return Single of List<DBProduct>
     */
    fun loadProductsByCategory(id: Int): Single<List<DBProduct>>
}

internal class DataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao
) : DataSource {
    override fun loadProductsLocalStorage(): Single<List<DBProduct>> {
        return productDao.loadProducts()
    }

    override fun loadProductsByCategory(id: Int): Single<List<DBProduct>> {
        return productDao.loadProduct(id).flatMap {
            productDao.loadProductsByCategory(category = it.category)
        }
    }

    override fun loadProduct(id: Int): Single<DBProduct> {
        return productDao.loadProduct(id = id)
    }

    override fun updateFavoriteStatus(id: Int, status: Boolean): Completable {
        return productDao.updateProduct(id = id, status = status)
    }

    override fun loadInitialData(): Completable {
        // load data from remote server and save to the local database
        return apiService.loadProducts()
            .flatMapCompletable { apiResponse ->
                val dbList = apiResponse.productResponses.map { product ->
                    product.toDBProduct(
                        product
                    )
                }
                productDao.insertProducts(list = dbList)
            }.doOnError { throwable ->
                Timber.e(throwable)
            }
    }
}