package com.jacksonueda.test.data.repository.repo

import androidx.paging.PagingData
import com.jacksonueda.test.data.api.GithubService
import com.jacksonueda.test.data.model.PageInfo
import com.jacksonueda.test.data.model.PagedResponse
import com.jacksonueda.test.data.repository.GithubRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    private lateinit var repository: GithubRepository

    @Mock
    private lateinit var githubService: GithubService

    @Before
    fun setup() {
        repository = GithubRepository(githubService)
    }

    @Test
    fun `should return an User when service is called`() {
        // Given
        val pageInfo = PageInfo("abc", 10, 1, "1.0")
        whenever(githubService.getUser(any(), any(), any())).thenReturn(
            Single.just(
                PagedResponse(
                    pageInfo,
                    listOf()
                )
            )
        )

        // When
        val testObserver = repository.getUser().test().assertValue{ it is PagingData }

        // Then
//        testObserver.await()
//        testObserver.assertComplete()
//        testObserver.assertNoErrors()
        verify(githubService, times(1)).getUser(any(), any(), any())
    }

}