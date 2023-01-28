package com.ditta.worldbeers.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ditta.worldbeers.network.Constants
import com.ditta.worldbeers.network.PunkRepository
import com.ditta.worldbeers.paging.BeerPagingSource

class BeerListViewModelTest(private val punkRepository: PunkRepository) : ViewModel() {

    val beer = Pager(
        config = PagingConfig(pageSize = Constants.MAX_RESULT_PER_PAGE, prefetchDistance = 2),
        pagingSourceFactory = {
            BeerPagingSource(punkRepository)
        }).flow.cachedIn(viewModelScope)

}


class BeerListViewModelFactory(
    private val punkRepository: PunkRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BeerListViewModelTest::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BeerListViewModelTest(
                punkRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}