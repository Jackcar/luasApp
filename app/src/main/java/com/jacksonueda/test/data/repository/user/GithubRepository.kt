package com.jacksonueda.test.data.repository.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.jacksonueda.test.data.api.GithubService
import com.jacksonueda.test.data.model.Repo
import com.jacksonueda.test.data.model.User
import com.jacksonueda.test.data.paging.RepoPagingSource
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class GithubRepository @Inject constructor(
    private val service: GithubService
) : IGithubRepository {

    override fun getUser(user: String): Single<User> =
        service.getUser(user).subscribeOn(Schedulers.io())

    override fun getUserRepo(user: String): Flowable<PagingData<Repo>> = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 2),
        pagingSourceFactory = { RepoPagingSource(service, user) }
    ).flowable

}