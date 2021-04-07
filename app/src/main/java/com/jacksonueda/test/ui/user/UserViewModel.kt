package com.jacksonueda.test.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.jacksonueda.test.data.model.User
import com.jacksonueda.test.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {

    // Disposable container that can hold multiple other Disposables, so later it can be cleaned
    // at once and avoid memory leak.
    private val compositeDisposable = CompositeDisposable()

    private val _users = MutableLiveData<PagingData<User>>()
    val users: LiveData<PagingData<User>> = _users

    @ExperimentalCoroutinesApi
    fun getUsers() {
        addToDisposable(
            repository.getUsers()
                .cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _users.postValue(it)
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