package com.ditta.worldbeers.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.ditta.worldbeers.model.Beer
import com.ditta.worldbeers.network.PunkRepository
import kotlinx.coroutines.launch

enum class PunkApiStatus { LOADING, ERROR, DONE }

class BeerListViewModel(private val punkRepository: PunkRepository) : ViewModel() {

    private val TAG = BeerListViewModel::class.java.simpleName

    private val _beer = MutableLiveData<List<Beer>>(emptyList())
    val beers: LiveData<List<Beer>> = _beer

    private val _status = MutableLiveData(PunkApiStatus.LOADING)
    val status: LiveData<PunkApiStatus> = _status

    init {
        getBeers()
    }

    fun getBeers() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Doing call to retrieve beer...")
                _status.value = PunkApiStatus.LOADING
                _beer.value = punkRepository.getBeer()
                _status.value = PunkApiStatus.DONE
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
                _status.value = PunkApiStatus.ERROR
                _beer.value = listOf()
            }
        }
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