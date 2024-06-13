package com.raka.data.di

import com.raka.data.api.ApiService
import com.raka.data.network.RetryNetworkInterceptor
import com.raka.data.utils.Constants
import com.raka.data.utils.Constants.DELAY_TIME_OUT
import com.raka.data.utils.Constants.RETRY_NETWORK_MAX
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    /**
     * provides RetryNetworkInterceptor Instance
     */
    @Provides
    @Singleton
    fun provideNetworkInterceptor() = RetryNetworkInterceptor(RETRY_NETWORK_MAX)

    /**
     * provides okHttp instance
     * it RetryNetworkInterceptor for retry mechanism
     * between each retry there is a delay 2 seconds
     * @param retryNetworkInterceptor of type RetryNetworkInterceptor
     */
    @Provides
    @Singleton
    fun provideOkHttp(retryNetworkInterceptor: RetryNetworkInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            // Readtimeout is maximum time of inactivity between two data packets when waiting for the server’s response
            .readTimeout(DELAY_TIME_OUT, TimeUnit.SECONDS)
            // Connecttimeout is time period in which our client should establish a connection with a target host
            .connectTimeout(DELAY_TIME_OUT, TimeUnit.SECONDS)
            // Writetimeout is maximum time of inactivity between two data packets when sending the request to the server
            .writeTimeout(DELAY_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(retryNetworkInterceptor)
            .retryOnConnectionFailure(true)
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
            .build()
}