package com.ditta.worldbeers.network


import com.ditta.worldbeers.model.Beer
import com.ditta.worldbeers.network.PunkApiConstant.BEERNAME_QUERY_PARAM_NAME
import com.ditta.worldbeers.network.PunkApiConstant.END_POINT_BEER
import com.ditta.worldbeers.network.PunkApiConstant.PAGE_QUERY_PARAM_NAME
import retrofit2.http.GET
import retrofit2.http.Query

object PunkApiConstant {
    const val BASE_URL = "https://api.punkapi.com/v2/"
    const val END_POINT_BEER: String = "beers"
    const val PAGE_QUERY_PARAM_NAME = "page"
    const val BEERNAME_QUERY_PARAM_NAME = "beer_name"
    const val INITIAL_PAGE_INDEX = 1
    const val MAX_RESULT_PER_PAGE = 25
}

interface PunkApiService {
    @GET(END_POINT_BEER)
    suspend fun getBeers(
        @Query(PAGE_QUERY_PARAM_NAME) page: Int? = null,
        @Query(BEERNAME_QUERY_PARAM_NAME) beerName: String? = null,
    ): List<Beer>
}

object PunkApi {
    val punkApiService: PunkApiService by lazy {
        NetworkModule.retrofitService.create(PunkApiService::class.java)
    }
}


