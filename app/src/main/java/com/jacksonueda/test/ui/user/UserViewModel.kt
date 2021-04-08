package com.jacksonueda.test.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.jacksonueda.test.data.model.Repo
import com.jacksonueda.test.data.repository.user.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: GithubRepository,
) : ViewModel() {

    // Disposable container that can hold multiple other Disposables, so later it can be cleaned
    // at once and avoid memory leak.
    private val compositeDisposable = CompositeDisposable()

    private val _repos = MutableLiveData<PagingData<Repo>>()
    val repos: LiveData<PagingData<Repo>> = _repos

    @ExperimentalCoroutinesApi
    fun getUsers() {
        addToDisposable(
            repository.getUserRepo("google")
                .cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _repos.postValue(it)
                    },
                    {
                        Log.e("UserViewModel", it.localizedMessage.orEmpty())
                    }
                )
        )
    }

    /**
     * Adds any new disposable to the compositeDisposable
     *
     * @param disposable
     */
    private fun addToDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    /**
     * Clears the CompositeDisposable when the ViewModel is destroyed.
     *
     */
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}