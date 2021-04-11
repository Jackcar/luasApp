package com.jacksonueda.test.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.jacksonueda.test.data.api.GithubService
import com.jacksonueda.test.data.model.Issue
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RepoIssuesPagingSource(
    private val service: GithubService,
    private val user: String,
    private val repo: String
) : RxPagingSource<Int, Issue>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Issue>> {
        val page = params.key ?: 1
        return service.getRepoIssues(user, repo, page)
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, Issue>> {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isNotEmpty()) page.plus(1) else null
                )
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }

    override fun getRefreshKey(state: PagingState<Int, Issue>): Int = 1

}
