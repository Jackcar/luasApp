package com.jacksonueda.test.data.repository.user

import androidx.paging.PagingData
import com.jacksonueda.test.data.model.User
import io.reactivex.rxjava3.core.Flowable

interface IUserRepository {

    fun getUsers(): Flowable<PagingData<User>>

}