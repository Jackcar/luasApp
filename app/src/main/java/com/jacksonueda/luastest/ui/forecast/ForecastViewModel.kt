package com.jacksonueda.luastest.ui.forecast

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jacksonueda.luastest.model.ErrorForecastData
import com.jacksonueda.luastest.model.LoadedForecastData
import com.jacksonueda.luastest.model.LoadingForecastData
import com.jacksonueda.luastest.model.StopInfo
import com.jacksonueda.luastest.model.enum.StopAbvEnum
import com.jacksonueda.luastest.repository.forecast.ForecastRepository
import com.jacksonueda.luastest.util.Response
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.time.LocalTime

class ForecastViewModel @ViewModelInject constructor(
    private val repository: ForecastRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val currentForecast: MutableLiveData<Response<StopInfo>> = MutableLiveData()

    init {
        addToDisposable(
            repository.forecast
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data ->
                        currentForecast.value = data
                            .let { it ->
                                when (it) {
                                    is LoadingForecastData -> Response.loading()
                                    is LoadedForecastData -> {
                                        val tramDirection: String =
                                            if (it.forecast.stopAbreviation == StopAbvEnum.STILLORGAN.abv) "Inbound" else "Outbound"
                                        it.forecast.lines =
                                            it.forecast.lines.filter { direction -> direction.name == tramDirection }
                                        Response.success(it.forecast)
                                    }
                                    is ErrorForecastData -> Response.error(
                                        it.error.localizedMessage,
                                        it.error
                                    )
                                }
                            }
                    },
                    {
                        currentForecast.value = Response.error(it.localizedMessage, it)
                    }
                )
        )
        refreshForecast()
    }

    fun refreshForecast(stopAbv: String = "") {
        val stopName = if (stopAbv == "") getStopName() else stopAbv
        addToDisposable(
            repository.loadForecast(stopName)
                .subscribe(
                    { Log.d("ForecastViewModel", "Success to refresh forecast") },
                    { Log.e("MainViewModel", "Error while refreshing forecast", it) }
                )
        )
    }

    private fun getStopName() = when {
        LocalTime.now().isAfter(LocalTime.MIDNIGHT) && LocalTime.now()
            .isBefore(LocalTime.NOON) -> StopAbvEnum.MARLBOROUGH.abv
        else -> StopAbvEnum.STILLORGAN.abv
    }

    private fun addToDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}