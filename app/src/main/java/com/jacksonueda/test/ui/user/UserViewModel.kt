package com.jacksonueda.test.ui.user

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.jacksonueda.test.data.model.User
import com.jacksonueda.test.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {

    @ExperimentalCoroutinesApi
    val users: Flowable<PagingData<User>> = repository.getUsers()

}