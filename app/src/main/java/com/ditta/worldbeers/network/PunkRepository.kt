package com.ditta.worldbeers.network

class PunkRepository {

    suspend fun getBeer(page: Int? = null, beerName: String? = null) =
        PunkApi.punkApiService.getBeers(page = page, beerName = beerName)

}
