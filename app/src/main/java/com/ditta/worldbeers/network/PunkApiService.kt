package com.ditta.worldbeers.network


import com.ditta.worldbeers.model.Beer
import com.ditta.worldbeers.model.GsonProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://api.punkapi.com/v2/"

interface PunkApiService {
    @GET("beers")
    suspend fun getBeers(): List<Beer>
}

object PunkApi {

    val retrofitService: PunkApiService by lazy {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonProvider.gson))
            .baseUrl(BASE_URL)
            .client(client)
            .build()

        retrofit.create(PunkApiService::class.java)
    }
}


