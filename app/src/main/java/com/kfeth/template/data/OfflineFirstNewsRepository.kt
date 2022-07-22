package com.kfeth.template.data

import com.kfeth.template.api.ArticleResponse
import com.kfeth.template.api.NewsApi
import com.kfeth.template.di.IoDispatcher
import com.kfeth.template.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineFirstNewsRepository @Inject constructor(
    private val api: NewsApi,
    private val dao: NewsDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : NewsRepository {

    override fun getArticle(articleId: String): Flow<Article> =
        dao.getArticle(articleId).map(ArticleEntity::asExternalModel)

    override suspend fun getLatestNewsStream(): Flow<Result<List<Article>>> = networkBoundResult(
        query = {
            dao.getAllArticles().map { entities ->
                entities.map(ArticleEntity::asExternalModel)
            }
        },
        fetch = { api.getLatestNews().articles },

        saveFetchResponse = { response ->
            val articles = response.map(ArticleResponse::asEntityModel)
            dao.deleteAndInsert(articles)
        }
    ).flowOn(ioDispatcher)
}

inline fun <ResultType, RequestType> networkBoundResult(
    crossinline query: suspend () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResponse: suspend (RequestType) -> Unit,
) =
    flow {
        val data = query().first()
        emit(Result.Loading(data))
        try {
            delay(500)
            saveFetchResponse(fetch())
            query().collect { emit(Result.Success(it)) }
        } catch (t: Throwable) {
            Timber.w(t)
            query().collect { emit(Result.Error(t, it)) }
        }
    }
