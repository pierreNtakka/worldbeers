package com.ditta.worldbeers.network

import com.ditta.worldbeers.model.Beer


interface PunkRepository {
    suspend fun getBeer(page: Int? = null, beerName: String? = null): List<Beer>
}

class PunkRepositoryImpl(private val punkApi: PunkApi) : PunkRepository {

    override suspend fun getBeer(page: Int?, beerName: String?): List<Beer> {
        return punkApi.getBeers(page = page, beerName = beerName)
    }
}
