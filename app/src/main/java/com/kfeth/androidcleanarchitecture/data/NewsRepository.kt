package com.kfeth.androidcleanarchitecture.data

import androidx.room.withTransaction
import com.kfeth.androidcleanarchitecture.util.Resource
import com.kfeth.androidcleanarchitecture.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val api: NewsApi,
    private val dao: NewsDao,
    private val db: NewsDatabase,
) {
    fun getBreakingNews(): Flow<Resource<List<ArticleEntity>>> = networkBoundResource(
        shouldFetch = { true },
        query = { dao.getAll() },
        fetch = { api.getBreakingNews().articles },
        saveFetchResult = { serverResp ->
            val articles: List<ArticleEntity> = serverResp.map { it.mapToEntity() }
            db.withTransaction {
                dao.deleteAll()
                dao.insert(articles)
            }
        },
        onFetchSuccess = { },
        onFetchFailed = { }
    )
}