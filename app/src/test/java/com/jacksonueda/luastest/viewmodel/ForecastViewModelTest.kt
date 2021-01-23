package com.jacksonueda.luastest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.jacksonueda.luastest.RxImmediateSchedulerRule
import com.jacksonueda.luastest.model.LoadedForecastData
import com.jacksonueda.luastest.model.StopInfo
import com.jacksonueda.luastest.repository.forecast.ForecastRepository
import com.jacksonueda.luastest.ui.forecast.ForecastViewModel
import com.jacksonueda.luastest.util.Response
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ForecastViewModelTest {

    private lateinit var viewModel: ForecastViewModel

    @Mock
    private lateinit var repository: ForecastRepository

    @Mock
    lateinit var mockLiveDataObserver: Observer<Response<StopInfo>>

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    @Before
    fun setup() {
        whenever(repository.forecast).thenReturn(
            BehaviorSubject.createDefault(
                LoadedForecastData(
                    StopInfo()
                )
            )
        )
        whenever(repository.loadForecast(any())).thenReturn(Completable.complete())

        viewModel = ForecastViewModel(repository, SavedStateHandle())
        viewModel.currentForecast.observeForever(mockLiveDataObserver)
    }

    @Test
    fun `should receive updates when data is refreshed`() {
        // Given

        // When
        viewModel.refreshForecast("sti")

        // Then
        verify(repository, atLeastOnce()).loadForecast(any())
        verify(mockLiveDataObserver, times(1)).onChanged(any())
    }

}