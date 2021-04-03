package com.jacksonueda.luastest.di

import com.jacksonueda.luastest.data.api.CharacterApi
import com.jacksonueda.luastest.data.repository.character.CharacterRepository
import com.jacksonueda.luastest.data.repository.character.ICharacterRepository
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
     * Provides the Character Repository object to make API calls.
     *
     * @param characterApi
     * @return CharacterRepository
     */
    @Provides
    @ViewModelScoped
    fun provideForecastRepository(characterApi: CharacterApi): ICharacterRepository =
        CharacterRepository(characterApi)

}