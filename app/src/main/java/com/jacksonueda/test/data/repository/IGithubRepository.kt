package com.jacksonueda.test.data.repository

import androidx.paging.PagingData
import com.jacksonueda.test.data.model.Issue
import com.jacksonueda.test.data.model.Repo
import io.reactivex.rxjava3.core.Flowable

interface IGithubRepository {

    fun getRepos(user: String): Flowable<PagingData<Repo>>

    fun getRepoIssues(user: String, repo: String): Flowable<PagingData<Issue>>

}