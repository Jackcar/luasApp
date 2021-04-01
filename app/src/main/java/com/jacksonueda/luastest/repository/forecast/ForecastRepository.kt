package com.jacksonueda.luastest.repository.forecast

import com.jacksonueda.luastest.api.LuasService
import com.jacksonueda.luastest.model.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Forecast Repository implementation, responsible to handle local and remote data. Uses the API endpoints
 * through the LuasService.
 *
 * @property luasService interface
 */
@Singleton
class ForecastRepository @Inject constructor(
    private val luasService: LuasService
) : IForecastRepository {

    // Emits the most recent item it has observed and all subsequent observed items to each subscribed Observer.
    private val forecastSubject = BehaviorSubject.createDefault(LoadingForecastData as ForecastData)

    // Expose the forecast Observable to subscribers receive the new data
    override val forecast: Observable<ForecastData> = forecastSubject

    /**
     * Loads the forecast data of a specific stop and emits the result to the forecastSubject.
     *
     * @param stopAbv stop name abbreviation
     * @return a completable
     */
    override fun loadForecast(stopAbv: String): Completable =
        luasService.loadForecast(stopAbv)
            .doOnSubscribe { forecastSubject.onNext(LoadingForecastData) }
            .doOnSuccess { forecastSubject.onNext(
                LoadedForecastData(
                    it
                )
            ) }
            .doOnError { forecastSubject.onNext(
                ErrorForecastData(
                    it
                )
            ) }
            .ignoreElement()
}