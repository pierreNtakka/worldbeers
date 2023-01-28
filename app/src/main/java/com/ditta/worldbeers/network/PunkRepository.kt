package com.ditta.worldbeers.network

class PunkRepository {

    suspend fun getBeer() = PunkApi.retrofitService.getBeers()

}
