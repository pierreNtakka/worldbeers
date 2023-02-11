package com.ditta.worldbeers.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ditta.worldbeers.model.Beer
import com.ditta.worldbeers.network.PunkApiConstant.INITIAL_PAGE_INDEX
import com.ditta.worldbeers.network.PunkRepository
import okio.IOException
import retrofit2.HttpException

class BeerPagingSource(
    private val repository: PunkRepository,
    private val currentSearch: String? = ""
) : PagingSource<Int, Beer>() {

    override fun getRefreshKey(state: PagingState<Int, Beer>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {

        val pageIndex = params.key ?: INITIAL_PAGE_INDEX
        return try {

            val response = if (currentSearch?.isEmpty() == true) {
                repository.getBeer(page = pageIndex)
            } else {
                repository.getBeer(page = pageIndex, beerName = currentSearch)
            }

            val nextKey =
                if (response.isEmpty()) {
                    null
                } else {
                    pageIndex.plus(1)
                }
            LoadResult.Page(
                data = response,
                prevKey = if (pageIndex == INITIAL_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}