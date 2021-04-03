package com.jacksonueda.luastest.data.repository.character

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.jacksonueda.luastest.data.api.CharacterApi
import com.jacksonueda.luastest.data.model.Character
import com.jacksonueda.luastest.data.paging.CharactersPagingSource
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val service: CharacterApi
) : ICharacterRepository {

    @ExperimentalCoroutinesApi
    override fun getAllCharacters(): Flowable<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = { CharactersPagingSource(service) }
    ).flowable

}