package com.ditta.worldbeers.network

import com.ditta.worldbeers.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class BaseRetrofitClient(
    private val baseUrl: String,
    private val converterFactory: Factory,
    private val connectionTimeoutSec: Long,
    private val readTimeoutSec: Long,
    private val writeTimeoutSec: Long
) {


    fun buildClient(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .connectTimeout(connectionTimeoutSec, TimeUnit.SECONDS)
            .readTimeout(readTimeoutSec, TimeUnit.SECONDS)
            .writeTimeout(writeTimeoutSec, TimeUnit.SECONDS).build()

        return Retrofit.Builder().addConverterFactory(converterFactory)
            .baseUrl(baseUrl).client(client).build()
    }
}