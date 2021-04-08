package com.jacksonueda.test.di

import com.jacksonueda.test.data.api.GithubService
import com.jacksonueda.test.data.repository.user.IGithubRepository
import com.jacksonueda.test.data.repository.user.GithubRepository
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
     * @param githubService
     * @return IUserRepository
     */
    @Provides
    @ViewModelScoped
    fun provideUserRepository(githubService: GithubService): IGithubRepository =
        GithubRepository(githubService)

}