package com.jacksonueda.luastest.ui.character

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.jacksonueda.luastest.data.model.Character
import com.jacksonueda.luastest.data.repository.character.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModel() {

    // Disposable container that can hold multiple other Disposables, so later it can be cleaned
    // at once and avoid memory leak.
    private val compositeDisposable = CompositeDisposable()

    @ExperimentalCoroutinesApi
    val characters: Flowable<PagingData<Character>> = repository.getAllCharacters()

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