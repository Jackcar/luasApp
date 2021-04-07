package com.jacksonueda.test.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.jacksonueda.test.data.api.RandomUserAPI
import com.jacksonueda.test.data.model.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UsersPagingSource(private val service: RandomUserAPI) :
    RxPagingSource<Int, User>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, User>> {
        return service.getUsers(params.key ?: 1, 10, "abc")
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, User>> {
                LoadResult.Page(
                    data = it.results,
                    prevKey = null,
                    nextKey = it.pageInfo.page + 1
                )
            }
            .doOnError {
                when (it) {
                    is IOException, is HttpException, is SocketTimeoutException, is UnknownHostException ->
                        LoadResult.Error<Int, Throwable>(it)
                    else -> throw it
                }
            }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int = 1

}