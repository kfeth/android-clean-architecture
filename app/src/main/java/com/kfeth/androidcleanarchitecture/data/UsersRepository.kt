package com.kfeth.androidcleanarchitecture.data

import com.kfeth.androidcleanarchitecture.domain.model.Result
import com.kfeth.androidcleanarchitecture.domain.model.Success
import com.kfeth.androidcleanarchitecture.domain.model.UserInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(private val api: UsersApi) {

    suspend fun getRandomUsers(): Result<UserInfo> {
        api.getUsers()
        return Success(UserInfo("test@test.com"))
    }
}