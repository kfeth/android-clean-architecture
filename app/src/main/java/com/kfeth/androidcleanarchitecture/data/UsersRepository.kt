package com.kfeth.androidcleanarchitecture.data

import android.util.Log
import com.kfeth.androidcleanarchitecture.domain.model.Result
import com.kfeth.androidcleanarchitecture.domain.model.Success
import com.kfeth.androidcleanarchitecture.domain.model.UserInfo
import com.kfeth.androidcleanarchitecture.util.Resource
import com.kfeth.androidcleanarchitecture.util.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(private val api: UsersApi) {

    /*
    suspend fun getUsers(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchFailed: (Throwable) -> Unit
    ): Flow<Resource<List<UserInfo>>> =
        networkBoundResource(
            query = {
                usersDao.getAllUsers()
            },
            fetch = {
                val response = api.getUsersTest()
                response.users
            },
            saveFetchResult = { serverUsers ->
                usersDb.withTransaction {
                    usersDao.deleteAllUsers()
                    usersDao.insertUsers(entityUsers)
                }
            },
            shouldFetch = { true },
            onFetchSuccess = onFetchSuccess,
            onFetchFailed = {
                if (it !is HttpException && it !is IOException) {
                    throw it
                }
                onFetchFailed(it)
            }
        )

     */

    suspend fun getRandomUsers(): Result<List<UserInfo>> {
        delay(1000)
        val users = api.getUsers().users
        delay(1000)
        return Success(listOf(UserInfo("test@test.com")))
    }
}