package com.jacksonueda.test.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.jacksonueda.test.data.api.GithubService
import com.jacksonueda.test.data.model.Issue
import com.jacksonueda.test.data.model.Repo
import com.jacksonueda.test.data.paging.RepoIssuesPagingSource
import com.jacksonueda.test.data.paging.RepoPagingSource
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class GithubRepository @Inject constructor(
    private val service: GithubService
) : IGithubRepository {

    override fun getRepos(user: String): Flowable<PagingData<Repo>> = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 10),
        pagingSourceFactory = { RepoPagingSource(service, user) }
    ).flowable

    override fun getRepoIssues(user: String, repo: String): Flowable<PagingData<Issue>> = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 10),
        pagingSourceFactory = { RepoIssuesPagingSource(service, user, repo) }
    ).flowable

}