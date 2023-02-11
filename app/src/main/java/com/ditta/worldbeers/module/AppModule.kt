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
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

val koinQualifierGson = named("GSON")
val koinQualifierConverterFactoryGson = named("CONVERTER_FACTORY_GSON")

val converterFactoryModule = module {
    single<Gson>(koinQualifierGson) {
        GsonBuilder().create()
    }

    single<Converter.Factory>(koinQualifierConverterFactoryGson) {
        GsonConverterFactory.create(
            get(
                koinQualifierGson
            )
        )
    }
}

val viewModelModule = module {

    viewModel {
        BeerListViewModel(get())
    }
}

val repositoryModule = module {
    single<PunkRepository> {
        PunkRepositoryImpl(get())
    }
}

val appModule = module {

    single {
        val retrofitBuilder = RetrofitFactory(
            baseUrl = BASE_URL,
            converterFactory = get(koinQualifierConverterFactoryGson),
            connectionTimeoutSec = CONNECT_TIMEOUT,
            readTimeoutSec = READ_TIMEOUT,
            writeTimeoutSec = WRITE_TIMEOUT
        )

        retrofitBuilder.buildClientRetrofit().create(PunkApi::class.java)
    }

    includes(viewModelModule, repositoryModule, converterFactoryModule)
}