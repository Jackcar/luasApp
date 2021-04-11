package com.jacksonueda.test.data.repository

import androidx.paging.PagingData
import com.jacksonueda.test.data.model.Issue
import com.jacksonueda.test.data.model.Repo
import com.jacksonueda.test.data.model.User
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface IGithubRepository {

    fun getUser(user: String): Single<User>

    fun getUserRepo(user: String): Flowable<PagingData<Repo>>

    fun getRepoIssues(user: String, repo: String): Flowable<PagingData<Issue>>

}