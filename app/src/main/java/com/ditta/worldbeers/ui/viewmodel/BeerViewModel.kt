package com.ditta.worldbeers.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ditta.worldbeers.network.PunkApiConstant.MAX_RESULT_PER_PAGE
import com.ditta.worldbeers.network.PunkRepository
import com.ditta.worldbeers.ui.paging.BeerPagingSource

class BeerListViewModel(private val punkRepository: PunkRepository) : ViewModel() {


    private var currentUserSearch = ""

    private var pagingSource: BeerPagingSource? = null
        get() {
            if (field == null || field?.invalid == true)
                field = BeerPagingSource(punkRepository, currentUserSearch)
            return field
        }

    val beer = Pager(
        config = PagingConfig(
            pageSize = MAX_RESULT_PER_PAGE,
            prefetchDistance = 2,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            pagingSource!!
        }).flow.cachedIn(viewModelScope)


    fun findByBeerName(beerName: String) {
        currentUserSearch = beerName.lowercase().replace(" ", "_")
        pagingSource?.invalidate()
    }

    fun sync() {
        currentUserSearch = ""
        pagingSource?.invalidate()
    }

}