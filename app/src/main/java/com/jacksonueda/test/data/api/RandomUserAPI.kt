package com.jacksonueda.test.data.api

import com.jacksonueda.test.data.model.PagedResponse
import com.jacksonueda.test.data.model.User
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserAPI {

    @GET("api/")
    fun getUsers(
        @Query("page") page: Int,
        @Query("results") results: Int,
        @Query("seed") seed: String
    ): Single<PagedResponse<User>>

}
