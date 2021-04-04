package com.jacksonueda.test.data.repository.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.jacksonueda.test.data.api.RandomUserAPI
import com.jacksonueda.test.data.model.User
import com.jacksonueda.test.data.paging.UsersPagingSource
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val service: RandomUserAPI
) : IUserRepository {

    @ExperimentalCoroutinesApi
    override fun getUsers(): Flowable<PagingData<User>> = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 2),
        pagingSourceFactory = { UsersPagingSource(service) }
    ).flowable

}