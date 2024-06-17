package com.raka.data.di

import com.raka.data.datasource.DataSource
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface DataSourceHelperModule {
    /**
     * provides a helper method to get DataSource Instance
     */
    fun getDataSource(): DataSource
}