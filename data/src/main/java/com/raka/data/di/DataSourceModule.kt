package com.raka.data.di

import com.raka.data.datasource.DataSource
import com.raka.data.datasource.DataSourceHelper
import com.raka.data.datasource.DataSourceHelperImpl
import com.raka.data.datasource.DataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    /**
     * provides DataSource Instance
     * @param dataSourceImpl of type DataSourceImpl
     * @return DataSource type
     */
    @Binds
    fun bindDataSource(dataSourceImpl: DataSourceImpl): DataSource

    /**
     * provides DatSourceHelper Instance
     * @param dataSourceHelperImpl of type DataSourceHelperImpl
     * return DataSourceHelper type
     */
    @Binds
    fun bindDataSourceHelper(dataSourceHelperImpl: DataSourceHelperImpl): DataSourceHelper
}