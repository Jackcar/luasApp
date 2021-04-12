package com.jacksonueda.test.ui.repo.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.jacksonueda.test.MainCoroutineRule
import com.jacksonueda.test.RxImmediateSchedulerRule
import com.jacksonueda.test.data.model.Issue
import com.jacksonueda.test.data.model.User
import com.jacksonueda.test.data.repository.GithubRepository
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepoDetailViewModelTest {

    private lateinit var viewModel: RepoDetailViewModel

    @Mock
    private lateinit var repository: GithubRepository

    @Mock
    lateinit var mockLiveDataObserver: Observer<PagingData<Issue>>

    @get:Rule
    var testSchedulerRule = RxImmediateSchedulerRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val ruleForLivaData = InstantTaskExecutorRule()


    @Before
    fun setup() {
        viewModel = RepoDetailViewModel(repository)
        viewModel.issues.observeForever(mockLiveDataObserver)
    }

    @Test
    fun `should receive repo update when data is refreshed successfully`() {
        // Given
        val issue = Issue(user = User())
        val pagingData = PagingData.from(listOf(issue))
        whenever(repository.getRepoIssues(any(), any())).thenReturn(Flowable.just(pagingData))

        // When
        viewModel.getIssues("google", "acai")

        // Then
        verify(repository, times(1)).getRepoIssues("google", "acai")
        verify(mockLiveDataObserver, times(1)).onChanged(any())
    }

    @Test
    fun `should not receive data update when data fails to refresh`() {
        // Given
        val exception = Exception()
        whenever(repository.getRepoIssues(any(), any())).thenReturn(Flowable.error(exception))

        // When
        viewModel.getIssues("google", "acai")

        // Then
        verify(repository, times(1)).getRepoIssues("google", "acai")
        verifyZeroInteractions(mockLiveDataObserver)
    }

}