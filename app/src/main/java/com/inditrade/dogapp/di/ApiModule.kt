package com.inditrade.dogapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.inditrade.dogapp.data.api.DogFeedApi
import com.inditrade.dogapp.data.api.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {
    @Provides
    fun provideApiService(retrofit: Retrofit): DogFeedApi =
        retrofit.create(DogFeedApi::class.java)


    @Provides
    fun provideOkHttpClient() = RetrofitInstance.buildOkHttpClient()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return RetrofitInstance.build(client, gson)
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()
}