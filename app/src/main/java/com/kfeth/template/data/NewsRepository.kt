package com.kfeth.template.data

import androidx.room.withTransaction
import com.kfeth.template.api.NewsApi
import com.kfeth.template.api.mapToEntity
import com.kfeth.template.util.Resource
import com.kfeth.template.util.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val api: NewsApi,
    private val dao: NewsDao,
    private val db: NewsDatabase
) {
    fun getArticle(articleId: String): Flow<Article> =
        dao.getArticle(articleId)

    fun getHeadlines(): Flow<Resource<List<Article>>> = networkBoundResource(
        query = { dao.getAllArticles() },
        fetch = {
            delay(500)
            api.getHeadlines().articles
        },
        saveFetchResult = { serverResp ->
            val articles: List<Article> = serverResp.map { it.mapToEntity() }
            db.withTransaction {
                dao.deleteAllArticles()
                dao.insert(articles)
            }
        }
    )
}
