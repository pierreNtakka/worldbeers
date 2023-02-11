package com.ditta.worldbeers.network

import com.ditta.worldbeers.BuildConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory(
    private val baseUrl: String,
    private val gson: Gson,
    private val connectionTimeoutSec: Long,
    private val readTimeoutSec: Long,
    private val writeTimeoutSec: Long
) {


    fun buildClientRetrofit(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .connectTimeout(connectionTimeoutSec, TimeUnit.SECONDS)
            .readTimeout(readTimeoutSec, TimeUnit.SECONDS)
            .writeTimeout(writeTimeoutSec, TimeUnit.SECONDS).build()

        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl).client(client).build()
    }
}