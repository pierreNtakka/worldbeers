package com.ditta.worldbeers.network


import com.ditta.worldbeers.BuildConfig
import com.ditta.worldbeers.model.Beer
import com.ditta.worldbeers.model.GsonProvider
import com.ditta.worldbeers.network.Constants.BASE_URL
import com.ditta.worldbeers.network.Constants.BEERNAME_QUERY_PARAM_NAME
import com.ditta.worldbeers.network.Constants.END_POINT_BEER
import com.ditta.worldbeers.network.Constants.PAGE_QUERY_PARAM_NAME
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface PunkApiService {
    @GET(END_POINT_BEER)
    suspend fun getBeers(
        @Query(PAGE_QUERY_PARAM_NAME) page: Int? = null,
        @Query(BEERNAME_QUERY_PARAM_NAME) beerName: String? = null,
    ): List<Beer>
}

object PunkApi {

    private const val CONNECT_TIMEOUT = 20L
    private const val READ_TIMEOUT = 60L
    private const val WRITE_TIMEOUT = 120L

    val retrofitService: PunkApiService by lazy {

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonProvider.gson))
            .baseUrl(BASE_URL)
            .client(client)
            .build()

        retrofit.create(PunkApiService::class.java)
    }
}


