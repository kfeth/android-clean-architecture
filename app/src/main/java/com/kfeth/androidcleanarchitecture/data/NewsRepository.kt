package com.kfeth.androidcleanarchitecture.data

import androidx.room.withTransaction
import com.kfeth.androidcleanarchitecture.api.NewsApi
import com.kfeth.androidcleanarchitecture.api.mapToEntity
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
        query = { dao.getAll() },
        fetch = { api.getBreakingNews().articles },
        saveFetchResult = { serverResp ->
            val articles: List<ArticleEntity> = serverResp.map { it.mapToEntity() }
            db.withTransaction {
                dao.deleteAll()
                dao.insert(articles)
            }
        }
    )

    /* Use this for no caching
    fun getBreakingNewsNoCache(): Flow<Resource<List<ArticleEntity>>> = networkBoundResource(
        fetch = {
            api.getBreakingNews().articles.map { it.mapToEntity() }
        }
    )
     */
}