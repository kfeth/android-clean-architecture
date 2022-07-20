package com.kfeth.template.data

import com.kfeth.template.api.ArticleResponse
import com.kfeth.template.api.NewsApi
import com.kfeth.template.api.mapToEntity
import com.kfeth.template.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val api: NewsApi,
    private val dao: NewsDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher // todo fix dispatcher usage
) {
    fun getNewsStream(): Flow<List<Article>> {
        return dao.getAllArticles().map { entities ->
            entities.map(ArticleEntity::asExternalModel)
        }.onEach {
            if (it.isEmpty()) {
                refreshNews()
            }
        }
    }

    suspend fun refreshNews() {
        delay(500)
        api.getHeadlines()
            .also { response ->
                val articles = response.articles.map(ArticleResponse::mapToEntity)
                dao.deleteAndInsert(articles)
            }
    }

    fun getArticle(articleId: String): Flow<Article> =
        dao.getArticle(articleId).map(ArticleEntity::asExternalModel)
}
