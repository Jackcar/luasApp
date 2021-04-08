package com.jacksonueda.test.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.jacksonueda.test.data.api.GithubService
import com.jacksonueda.test.data.model.Repo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RepoPagingSource(
    private val service: GithubService,
    private val user: String
) : RxPagingSource<Int, Repo>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Repo>> {
        val page = params.key ?: 1
        return service.getUserRepo(user, page)
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, Repo>> {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = page.plus(1)
                )
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int = 1

}
