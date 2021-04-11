package com.jacksonueda.test.ui.repo.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.jacksonueda.test.data.model.Issue
import com.jacksonueda.test.data.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class RepoDetailViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _issues = MutableLiveData<PagingData<Issue>>()
    val issues: LiveData<PagingData<Issue>> = _issues

    @ExperimentalCoroutinesApi
    fun getIssues(user: String, repoName: String) {
        addToDisposable(
            repository.getRepoIssues(user, repoName)
                .cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _issues.postValue(it)
                    },
                    {
                        Log.e("RepoDetailViewModel", it.localizedMessage.orEmpty())
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