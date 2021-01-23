package com.jacksonueda.luastest.di

import com.jacksonueda.luastest.api.LuasService
import com.jacksonueda.luastest.repository.forecast.ForecastRepository
import com.jacksonueda.luastest.repository.forecast.IForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideForecastRepository(luasService: LuasService): IForecastRepository = ForecastRepository(luasService)

}