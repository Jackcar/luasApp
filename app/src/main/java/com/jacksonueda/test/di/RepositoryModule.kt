package com.jacksonueda.test.di

import com.jacksonueda.test.data.api.RandomUserAPI
import com.jacksonueda.test.data.repository.user.IUserRepository
import com.jacksonueda.test.data.repository.user.UserRepository
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
     * Provides the User Repository object to make API calls.
     *
     * @param randomUserAPI
     * @return IUserRepository
     */
    @Provides
    @ViewModelScoped
    fun provideUserRepository(randomUserAPI: RandomUserAPI): IUserRepository =
        UserRepository(randomUserAPI)

}