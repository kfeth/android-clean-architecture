package com.kfeth.androidcleanarchitecture.data

import androidx.room.withTransaction
import com.kfeth.androidcleanarchitecture.util.Resource
import com.kfeth.androidcleanarchitecture.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val api: UsersApi,
    private val dao: UserDao,
    private val db: AppDatabase,
) {
    fun getUsers(): Flow<Resource<List<UserEntity>>> = networkBoundResource(
        query = { dao.getAllUsers() },
        fetch = { api.getUsers().users },
        saveFetchResult = { serverUsers ->
            val users = serverUsers.map { UserEntity(it.email) }
            db.withTransaction {
                dao.deleteAllUsers()
                dao.insertUsers(users)
            }
        },
        shouldFetch = { true },
        onFetchSuccess = { },
        onFetchFailed = {
            if (it !is HttpException && it !is IOException) {
                throw it
            }
        }
    )
}