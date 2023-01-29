package com.ditta.worldbeers.network


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


interface PunkApiService {
    @GET(END_POINT_BEER)
    suspend fun getBeers(
        @Query(PAGE_QUERY_PARAM_NAME) page: Int? = null,
        @Query(BEERNAME_QUERY_PARAM_NAME) beerName: String? = null,
    ): List<Beer>
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


