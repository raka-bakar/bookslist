package com.raka.data.di

import com.raka.data.DataSource
import com.raka.data.DataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {

    @Binds
    fun bindDataSource(repository: DataSourceImpl): DataSource
}