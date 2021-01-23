package com.jacksonueda.luastest.model

sealed class ForecastData

object LoadingForecastData : ForecastData()
class LoadedForecastData(val forecast: StopInfo): ForecastData()
class ErrorForecastData(val error: Throwable) : ForecastData()