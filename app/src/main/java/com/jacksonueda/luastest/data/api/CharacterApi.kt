package com.jacksonueda.luastest.data.api

import com.jacksonueda.luastest.data.model.Character
import com.jacksonueda.luastest.data.model.PagedResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    @GET("character/")
    fun getAllCharacters(@Query("page") page: Int): Single<PagedResponse<Character>>

}
