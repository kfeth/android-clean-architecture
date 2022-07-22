package com.kfeth.template.data

import com.kfeth.template.api.ArticleResponse
import com.kfeth.template.api.NewsApi
import com.kfeth.template.di.IoDispatcher
import com.kfeth.template.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnlineOnlyNewsRepository @Inject constructor(
    private val api: NewsApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : NewsRepository {

    // No db to query
    override fun getArticle(articleId: String): Flow<Article> = emptyFlow()

    override suspend fun getLatestNewsStream(): Flow<Result<List<Article>>> = networkResult(
        fetch = { api.getLatestNews().articles },

        handleResponse = { response ->
            response.map(ArticleResponse::asExternalModel)
        }
    ).flowOn(ioDispatcher)
}

private inline fun <ResultType, RequestType> networkResult(
    crossinline fetch: suspend () -> RequestType,
    crossinline handleResponse: suspend (RequestType) -> ResultType,
) =
    flow {
        emit(Result.Loading<ResultType>(null))
        try {
            delay(500)
            val data = handleResponse(fetch())
            emit(Result.Success(data))
        } catch (t: Throwable) {
            Timber.w(t)
            emit(Result.Error(t))
        }
    }
