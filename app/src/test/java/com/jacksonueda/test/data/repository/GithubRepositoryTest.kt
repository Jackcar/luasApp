package com.jacksonueda.test.data.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.jacksonueda.test.data.api.GithubService
import com.jacksonueda.test.data.model.Issue
import com.jacksonueda.test.data.model.Repo
import com.jacksonueda.test.data.model.User
import com.jacksonueda.test.data.paging.RepoIssuesPagingSource
import com.jacksonueda.test.data.paging.RepoPagingSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GithubRepositoryTest {

    private lateinit var repository: GithubRepository

    @Mock
    private lateinit var githubService: GithubService

    @Before
    fun setup() {
        repository = GithubRepository(githubService)
    }

    @Test
    fun `should return a Repo PagingData when repository is called`() {
        // When
        val testObserver = repository.getRepos("google").test()

        // Then
        testObserver.assertValueCount(1)
        testObserver.assertValue { it is PagingData<Repo> }
        testObserver.assertNoErrors()
    }

    @Test
    fun `should return an error on getRepos service call when user does not exist`() {
        // Given
        whenever(githubService.getRepos(any(), any())).thenReturn(
            Single.error(
                HttpException(Response.error<Any>(404, "Not found".toResponseBody()))
            )
        )
        val pagingSource = RepoPagingSource(githubService, "google213123")
        val params = PagingSource.LoadParams.Refresh(1, 2, false)

        // When
        val loadResult = pagingSource.loadSingle(params).test()

        // Then
        loadResult.await()
        loadResult.assertComplete()
        loadResult.assertValue { (it as? PagingSource.LoadResult.Error)?.throwable is HttpException }

        loadResult.dispose()
    }

    @Test
    fun `should return an empty list on getRepos service call when user does not have any repository`() {
        // Given
        whenever(githubService.getRepos(any(), any())).thenReturn(Single.just(listOf()))
        val pagingSource = RepoPagingSource(githubService, "google1")
        val params = PagingSource.LoadParams.Refresh(1, 2, false)

        // When
        val loadResult = pagingSource.loadSingle(params).test()

        // Then
        loadResult.await()
        loadResult.assertNoErrors()
        loadResult.assertValueCount(1)
        loadResult.assertValue { (it as? PagingSource.LoadResult.Page)?.data?.isEmpty() == true }

        loadResult.dispose()
    }

    @Test
    fun `should return an empty list on getRepos service call when is the last page`() {
        // Given
        whenever(githubService.getRepos(any(), any())).thenReturn(Single.just(listOf()))
        val pagingSource = RepoPagingSource(githubService, "google")
        val params = PagingSource.LoadParams.Refresh(100, 2, false)

        // When
        val loadResult = pagingSource.loadSingle(params).test()

        // Then
        loadResult.await()
        loadResult.assertNoErrors()
        loadResult.assertValueCount(1)
        loadResult.assertValue { (it as? PagingSource.LoadResult.Page)?.data?.isEmpty() == true }

        loadResult.dispose()
    }

    @Test
    fun `should return a list of repos on getRepos service call when user has at least one repository`() {
        // Given
        val repo = Repo(owner = User())
        whenever(githubService.getRepos(any(), any())).thenReturn(
            Single.just(
                listOf(
                    repo
                )
            )
        )
        val pagingSource = RepoPagingSource(githubService, "google")
        val params = PagingSource.LoadParams.Refresh(1, 2, false)

        // When
        val loadResult = pagingSource.loadSingle(params).test()

        // Then
        loadResult.await()
        loadResult.assertNoErrors()
        loadResult.assertValueCount(1)
        loadResult.assertValue {
            (it as? PagingSource.LoadResult.Page)?.data?.let { data ->
                data.isNotEmpty() && data.contains(repo)
            } == true
        }

        loadResult.dispose()
    }

    @Test
    fun `should return a Issue PagingData when repository is called`() {
        // When
        val testObserver = repository.getRepoIssues("google", "acai").test()

        // Then
        testObserver.assertValueCount(1)
        testObserver.assertValue { it is PagingData<Issue> }
        testObserver.assertNoErrors()
    }

    @Test
    fun `should return an error on getRepoIssues service call when user does not exist`() {
        // Given
        whenever(githubService.getRepoIssues(any(), any(), any())).thenReturn(
            Single.error(
                HttpException(Response.error<Any>(404, "Not found".toResponseBody()))
            )
        )
        val pagingSource = RepoIssuesPagingSource(githubService, "google213123", "acai")
        val params = PagingSource.LoadParams.Refresh(1, 2, false)

        // When
        val loadResult = pagingSource.loadSingle(params).test()

        // Then
        loadResult.await()
        loadResult.assertComplete()
        loadResult.assertValue { (it as? PagingSource.LoadResult.Error)?.throwable is HttpException }

        loadResult.dispose()
    }

    @Test
    fun `should return an error on getRepoIssues service call when repository does not exist`() {
        // Given
        whenever(githubService.getRepoIssues(any(), any(), any())).thenReturn(
            Single.error(
                HttpException(Response.error<Any>(404, "Not found".toResponseBody()))
            )
        )
        val pagingSource = RepoIssuesPagingSource(githubService, "google", "acai123")
        val params = PagingSource.LoadParams.Refresh(1, 2, false)

        // When
        val loadResult = pagingSource.loadSingle(params).test()

        // Then
        loadResult.await()
        loadResult.assertComplete()
        loadResult.assertValue { (it as? PagingSource.LoadResult.Error)?.throwable is HttpException }

        loadResult.dispose()
    }

    @Test
    fun `should return an empty list on getRepoIssue service call when repository has no issues`() {
        // Given
        whenever(githubService.getRepoIssues(any(), any(), any())).thenReturn(Single.just(listOf()))
        val pagingSource = RepoIssuesPagingSource(githubService, "google", "acai")
        val params = PagingSource.LoadParams.Refresh(1, 2, false)

        // When
        val loadResult = pagingSource.loadSingle(params).test()

        // Then
        loadResult.await()
        loadResult.assertNoErrors()
        loadResult.assertValueCount(1)
        loadResult.assertValue { (it as? PagingSource.LoadResult.Page)?.data?.isEmpty() == true }

        loadResult.dispose()
    }

    @Test
    fun `should return a list of issues on getRepoIssue service call when repository has issues`() {
        // Given
        val issue = Issue(user = User())
        whenever(githubService.getRepoIssues(any(), any(), any())).thenReturn(
            Single.just(
                listOf(
                    issue
                )
            )
        )
        val pagingSource = RepoIssuesPagingSource(githubService, "google", "acai")
        val params = PagingSource.LoadParams.Refresh(1, 2, false)

        // When
        val loadResult = pagingSource.loadSingle(params).test()

        // Then
        loadResult.await()
        loadResult.assertNoErrors()
        loadResult.assertValueCount(1)
        loadResult.assertValue {
            (it as? PagingSource.LoadResult.Page)?.data?.let { data ->
                data.isNotEmpty() && data.contains(issue)
            } == true
        }

        loadResult.dispose()
    }

}