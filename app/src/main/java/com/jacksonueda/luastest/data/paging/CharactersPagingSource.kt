package com.jacksonueda.luastest.data.paging

import android.net.Uri
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.jacksonueda.luastest.data.api.CharacterApi
import com.jacksonueda.luastest.data.model.Character
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class CharactersPagingSource(private val service: CharacterApi) :
    RxPagingSource<Int, Character>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Character>> {
        return service.getAllCharacters(params.key ?: 1)
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, Character>> {
                LoadResult.Page(
                    data = it.results,
                    prevKey = null,
                    nextKey = if(it.pageInfo.next != null) Uri.parse(it.pageInfo.next).getQueryParameter("page")?.toInt() else null
                )
            }
            .doOnError {
                when (it) {
                    is IOException -> LoadResult.Error<Int, Throwable>(it)
                    is HttpException -> LoadResult.Error<Int, Throwable>(it)
                    else -> throw it
                }
            }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int = 1

}