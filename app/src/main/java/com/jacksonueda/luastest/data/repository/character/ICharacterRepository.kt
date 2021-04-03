package com.jacksonueda.luastest.data.repository.character

import androidx.paging.PagingData
import com.jacksonueda.luastest.data.model.Character
import io.reactivex.rxjava3.core.Flowable

interface ICharacterRepository {

    fun getAllCharacters(): Flowable<PagingData<Character>>

}