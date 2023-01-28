package com.ditta.worldbeers.network

class PunkRepository {

    suspend fun getBeer(page: Int) = PunkApi.retrofitService.getBeers(page = page)

}
