package com.jacksonueda.test.data.repository

import com.jacksonueda.test.data.api.RandomUserAPI
import com.jacksonueda.test.data.model.PageInfo
import com.jacksonueda.test.data.model.PagedResponse
import com.jacksonueda.test.data.repository.user.UserRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    private lateinit var repository: UserRepository

    @Mock
    private lateinit var randomUserAPI: RandomUserAPI

    @Before
    fun setup() {
        repository = UserRepository(randomUserAPI)
    }

    @Test
    fun `should return an User when service is called`() {
        // Given
        val pageInfo = PageInfo("abc", 10, 1, "1.0")
        whenever(randomUserAPI.getUsers(any(), any(), any())).thenReturn(
            Single.just(
                PagedResponse(
                    pageInfo,
                    listOf()
                )
            )
        )

        // When
//        val testObserver = repository.getUsers().test()

        // Then
//        testObserver.await()
//        testObserver.assertComplete()
//        testObserver.assertNoErrors()
//        verify(randomUserAPI, atLeastOnce()).getUsers(any(), any(), any())
    }

}