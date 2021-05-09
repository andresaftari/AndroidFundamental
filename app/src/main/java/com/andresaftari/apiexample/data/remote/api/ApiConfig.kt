package com.andresaftari.apiexample.data.remote.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    private var retrofit: Retrofit? = null
    private val duration = 120L
    private val second = TimeUnit.SECONDS

    private val okHttpBuilder = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(duration, second)
        .readTimeout(duration, second)
        .writeTimeout(duration, second)
        .build()

    fun <T> createService(serviceClass: Class<T>?): T {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(okHttpBuilder)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(serviceClass!!)
    }

    val service: ApiEndpoint by lazy { createService(ApiEndpoint::class.java) }
}