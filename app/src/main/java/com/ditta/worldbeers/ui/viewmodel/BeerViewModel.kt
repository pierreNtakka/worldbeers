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

class BeerListViewModel(private val punkRepository: PunkRepository) : ViewModel() {

    val beer = Pager(
        config = PagingConfig(pageSize = 1, prefetchDistance = 2),
        pagingSourceFactory = {
            BeerPagingSource(punkRepository)
        }).flow.cachedIn(viewModelScope)


    fun findByBeerName(beerName: String) {
        val beerNameReplaced = beerName.replace(" ", "_")

    }

}


class BeerListViewModelFactory(
    private val punkRepository: PunkRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BeerListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BeerListViewModel(
                punkRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}