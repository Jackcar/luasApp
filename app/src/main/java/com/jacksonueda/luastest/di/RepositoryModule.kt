package com.jacksonueda.luastest.di

import com.jacksonueda.luastest.api.LuasService
import com.jacksonueda.luastest.repository.forecast.ForecastRepository
import com.jacksonueda.luastest.repository.forecast.IForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Module which provides all required dependencies about the repository
 */
@Module

// Indicates which component scope the module should be installed, in this case in the ViewModelComponent
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    /**
     * Provides the Forecast Repository object to make API calls.
     *
     * @param luasService
     * @return ForecastRepository
     */
    @Provides
    @ViewModelScoped
    fun provideForecastRepository(luasService: LuasService): IForecastRepository = ForecastRepository(luasService)

}