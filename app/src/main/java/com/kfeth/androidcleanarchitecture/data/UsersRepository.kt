package com.kfeth.androidcleanarchitecture.data

import com.kfeth.androidcleanarchitecture.domain.model.Result
import com.kfeth.androidcleanarchitecture.domain.model.Success
import com.kfeth.androidcleanarchitecture.domain.model.UserInfo
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(private val api: UsersApi) {

    suspend fun getRandomUsers(): Result<List<UserInfo>> {
        delay(1000)
        api.getUsers()
        delay(1000)
        return Success(listOf(UserInfo("test@test.com")))
    }
}