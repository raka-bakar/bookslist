package com.raka.data

import android.content.Context
import com.raka.data.di.DataSourceHelper
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

/**
 * This class is the entry point to the module, it exposes the data calls to the app
 */
class DataProvider @Inject constructor(context: Context) {
    private val dataSourceHelper =
        EntryPointAccessors
            .fromApplication(context = context, entryPoint = DataSourceHelper::class.java)

    fun loadProducts() = dataSourceHelper.getDataSource().loadProductsLocalStorage()

    fun loadProduct(id: Int) = dataSourceHelper.getDataSource().loadProduct(id = id)

    fun updateFavoriteStatus(id: Int, status: Boolean) =
        dataSourceHelper.getDataSource().updateFavoriteStatus(id = id, status = status)

    fun loadInitialData() = dataSourceHelper.getDataSource().loadInitialData()

    fun loadProductsByCategory(id: Int) =
        dataSourceHelper.getDataSource().loadProductsByCategory(id = id)
}