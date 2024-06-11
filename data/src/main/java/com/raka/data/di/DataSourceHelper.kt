package com.raka.data.di

import com.raka.data.DataSource
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface DataSourceHelper {
    fun getDataSource(): DataSource
}