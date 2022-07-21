package com.kfeth.template.data

import com.kfeth.template.api.ArticleResponse
import com.kfeth.template.api.NewsApi
import com.kfeth.template.api.mapToEntity
import com.kfeth.template.di.IoDispatcher
import com.kfeth.template.util.Result
import com.kfeth.template.util.networkBoundResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val api: NewsApi,
    private val dao: NewsDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    fun getArticle(articleId: String): Flow<Article> =
        dao.getArticle(articleId).map(ArticleEntity::asExternalModel)

    suspend fun getLatestNews(): Flow<Result<List<Article>>> = networkBoundResult(
        query = {
            dao.getAllArticles().map { entities ->
                entities.map(ArticleEntity::asExternalModel)
            }
        },
        fetch = {
            delay(500)
            api.getLatestNews().articles
        },
        saveFetchResult = { result ->
            val articles = result.map(ArticleResponse::mapToEntity)
            dao.deleteAndInsert(articles)
        }
    ).flowOn(ioDispatcher)
}
