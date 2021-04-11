package com.jacksonueda.test.ui.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.jacksonueda.test.RxImmediateSchedulerRule
import com.jacksonueda.test.data.model.Picture
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
class RepoViewModelTest {

    private lateinit var viewModel: RepoViewModel

    @Mock
    private lateinit var repository: GithubRepository

    @Mock
    lateinit var mockLiveDataObserver: Observer<PagingData<User>>

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = RepoViewModel(repository)
        viewModel.repos.observeForever(mockLiveDataObserver)
    }

    @Test
    fun `should receive users update when data is refreshed successfully`() {
        // Given
        val user = User(Id("", ""), Name("", "", ""), "", Picture("", "", ""))
        val pagingData = PagingData.from(listOf(user))
        whenever(repository.getUser()).thenReturn(Flowable.just(pagingData))

        // When
        viewModel.getRepos()

        // Then
        verify(repository, times(1)).getUser()
        verify(mockLiveDataObserver, times(1)).onChanged(eq(pagingData))
    }

    @Test
    fun `should not receive data update when data fails to refresh`() {
        // Given
        val exception = Exception()
        whenever(repository.getUser()).thenReturn(Flowable.error(exception))

        // When
        viewModel.getRepos()

        // Then
        verify(repository, times(1)).getUser()
        verifyZeroInteractions(mockLiveDataObserver)
    }

}