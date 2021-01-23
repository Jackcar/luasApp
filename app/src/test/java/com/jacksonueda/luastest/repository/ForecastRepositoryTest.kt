package com.jacksonueda.luastest.repository

import com.jacksonueda.luastest.api.LuasService
import com.jacksonueda.luastest.model.StopInfo
import com.jacksonueda.luastest.repository.forecast.ForecastRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ForecastRepositoryTest {

    private lateinit var repository: ForecastRepository

    @Mock
    private lateinit var luasService: LuasService

    @Before
    fun setup() {
        repository = ForecastRepository(luasService)
    }

    @Test
    fun `should return success on loadForecast call`() {
        // Given
        val mockData = StopInfo()
        whenever(luasService.loadForecast(any())).thenReturn(Single.just(mockData))

        // When
        val testObserver = repository.loadForecast("sti").test()

        // Then
        testObserver.await()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        verify(luasService, atLeastOnce()).loadForecast(any())
    }

    @Test
    fun `should return error on loadForecast exception`() {
        // Given
        val exception = Exception()
        whenever(luasService.loadForecast(any())).thenReturn(Single.error(exception))

        // When
        val testObserver = repository.loadForecast("sti").test()

        // Then
        testObserver.await()
        testObserver.assertNotComplete()
        testObserver.assertError(exception)
        verify(luasService, atLeastOnce()).loadForecast(any())
    }

}