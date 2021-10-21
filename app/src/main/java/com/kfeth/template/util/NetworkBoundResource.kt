package com.kfeth.template.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber

/* https://developer.android.com/jetpack/guide#addendum */
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: suspend () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline onFetchSuccess: () -> Unit = { },
    crossinline onFetchFailed: (Throwable) -> Unit = { }
) =
    channelFlow {
        val data = query().first()

        if (shouldFetch(data)) {
            val loading = launch {
                query().collect { send(Resource.Loading(it)) }
            }
            try {
                saveFetchResult(fetch())
                onFetchSuccess()
                loading.cancel()
                query().collect { send(Resource.Success(it)) }
            } catch (t: Throwable) {
                Timber.w(t)
                onFetchFailed(t)
                loading.cancel()
                query().collect { send(Resource.Error(t, it)) }
            }
        } else {
            query().collect { send(Resource.Success(it)) }
        }
    }.flowOn(Dispatchers.IO)
