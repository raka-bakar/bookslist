package com.raka.data.di

import com.raka.data.api.ApiService
import com.raka.data.network.NetworkInterceptor
import com.raka.data.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    /**
     * provides NetworkInterceptor Instance
     */
    @Provides
    @Singleton
    fun provideNetworkInterceptor() = NetworkInterceptor()

    /**
     * provides okHttp instance
     * @param networkMonitor of type NetworkMonitor
     */
    @Provides
    @Singleton
    fun provideOkHttp(networkInterceptor: NetworkInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkInterceptor)
            .build()
    }

    /**
     * provides Moshi instance configured with Json converters
     */
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /**
     * provides retrofit Api for network calls
     * @param retrofit of type Retrofit
     */
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /**
     * provides retrofit instance
     * @param httpClient of type OkHttpClient
     * @param moshi of type Moshi
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
}