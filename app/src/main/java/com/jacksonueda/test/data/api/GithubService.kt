package com.jacksonueda.test.data.api

import com.jacksonueda.test.data.model.Issue
import com.jacksonueda.test.data.model.Repo
import com.jacksonueda.test.data.model.User
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("users/{user}")
    fun getUser(@Path("user") user: String): Single<User>

    @GET("users/{user}/repos")
    fun getUserRepo(@Path("user") user: String, @Query("page") page: Int): Single<List<Repo>>

    @GET("repos/{user}/{repo}/issues")
    fun getRepoIssues(
        @Path("user") user: String,
        @Path("repo") repo: String,
        @Query("page") page: Int
    ): Single<List<Issue>>

}
