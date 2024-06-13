package com.raka.data

import android.content.Context
import com.raka.data.di.DataSourceHelper
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

/**
 * This class exposes the data calls to the app
 */
class DataProvider @Inject constructor(context: Context) {
    private val dataSourceHelper =
        EntryPointAccessors
            .fromApplication(context = context, entryPoint = DataSourceHelper::class.java)

    fun loadBooks() = dataSourceHelper.getDataSource().loadBooks()

    fun loadBook(id: Int) = dataSourceHelper.getDataSource().loadBook(id = id)
}