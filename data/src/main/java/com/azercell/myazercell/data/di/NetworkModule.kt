package com.azercell.myazercell.data.di

import com.azercell.myazercell.BuildConfig
import com.azercell.myazercell.data.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideGsonFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(httpLoggingInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttp: OkHttpClient,
        factory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(factory)
        .client(okHttp)
        .baseUrl(BuildConfig.SERVER_URL)
        .build()

}