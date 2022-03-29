package com.inditrade.dogapp.data.api

import android.net.Uri
import com.google.gson.Gson
import com.inditrade.dogapp.BuildConfig
import com.inditrade.dogapp.utils.exceptions.InvalidUrlException
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private const val timeOutInSeconds = 15L

    fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                }
                connectTimeout(timeOutInSeconds, TimeUnit.SECONDS)
                writeTimeout(timeOutInSeconds, TimeUnit.SECONDS)
                readTimeout(timeOutInSeconds, TimeUnit.SECONDS)
            }
            .build()
    }

    fun build(okHttpClient: OkHttpClient, gsonInstance: Gson): Retrofit {
        if (!Uri.parse(BuildConfig.DOG_BASE_URL).isAbsolute) {
            throw InvalidUrlException(INVALID_HN_API_URL)
        }
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.DOG_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonInstance))
            .build()
    }
}
