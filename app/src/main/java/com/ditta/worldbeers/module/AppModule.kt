package com.ditta.worldbeers.module

import com.ditta.worldbeers.network.PunkApi
import com.ditta.worldbeers.network.PunkApiConstant.BASE_URL
import com.ditta.worldbeers.network.PunkApiConstant.CONNECT_TIMEOUT
import com.ditta.worldbeers.network.PunkApiConstant.READ_TIMEOUT
import com.ditta.worldbeers.network.PunkApiConstant.WRITE_TIMEOUT
import com.ditta.worldbeers.network.PunkRepository
import com.ditta.worldbeers.network.PunkRepositoryImpl
import com.ditta.worldbeers.network.RetrofitFactory
import com.ditta.worldbeers.ui.viewmodel.BeerListViewModel
import com.google.gson.GsonBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    single {
        GsonBuilder()
            .create()
    }

    single {

        val retrofitBuilder = RetrofitFactory(
            baseUrl = BASE_URL,
            gson = get(),
            connectionTimeoutSec = CONNECT_TIMEOUT,
            readTimeoutSec = READ_TIMEOUT,
            writeTimeoutSec = WRITE_TIMEOUT
        )

        retrofitBuilder.buildClientRetrofit().create(PunkApi::class.java)
    }

    single<PunkRepository> {
        PunkRepositoryImpl(get())
    }

    viewModel {
        BeerListViewModel(get())
    }


}