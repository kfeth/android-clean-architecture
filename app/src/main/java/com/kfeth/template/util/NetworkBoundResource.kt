package com.kfeth.template.util

import com.kfeth.template.data.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber

/* https://developer.android.com/jetpack/guide#addendum */
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: suspend () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
) =
    flow<Result<ResultType>> {
        val data = query().first()
        emit(Result.Loading(data))
        saveFetchResult(fetch())
        query().collect { emit(Result.Success(it)) }



//        val data = query().first()

//        if (shouldFetch(data)) {
//            val loading = launch {
//                query().collect { send(Result.Loading(it)) }
//            }
//            try {
//                saveFetchResult(fetch())
//                loading.cancel()
//                query().collect { send(Resource.Success(it)) }
//            } catch (t: Throwable) {
//                Timber.w(t)
//                loading.cancel()
//                query().collect { send(Resource.Error(t, it)) }
//            }
//        } else {
//            query().collect { send(Resource.Success(it)) }
//        }
    }
