package com.example.pagingbasic.di

import com.example.pagingbasic.data.PostRepository
import com.example.pagingbasic.data.api.JsonPlaceHolderApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideBaseUrl() = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().setLenient().create()!!

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideJsonPlaceholderApi(client: OkHttpClient): JsonPlaceHolderApi = Retrofit.Builder()
        .baseUrl(provideBaseUrl())
        .client(provideOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(provideGson()))
        .client(client)
        .build()
        .create(JsonPlaceHolderApi::class.java)

    @Provides
    fun providePostRepository(api: JsonPlaceHolderApi): PostRepository {
        return PostRepository(api)
    }
}