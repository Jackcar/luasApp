package com.jacksonueda.test.ui.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.jacksonueda.test.RxImmediateSchedulerRule
import com.jacksonueda.test.data.repository.user.UserRepository
import com.jacksonueda.test.ui.user.UserViewModel
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
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
class UserViewModelTest {

    private lateinit var viewModel: UserViewModel

    @Mock
    private lateinit var repository: UserRepository

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = UserViewModel(repository)
    }

    @Test
    fun `should receive users update when data is refreshed successfully`() {
        // Given
        whenever(repository.getUsers()).thenReturn(Flowable.just(PagingData.from(listOf())))

        // When
        viewModel.users.subscribe()

        // Then
        verify(repository, times(1)).getUsers()
    }


}