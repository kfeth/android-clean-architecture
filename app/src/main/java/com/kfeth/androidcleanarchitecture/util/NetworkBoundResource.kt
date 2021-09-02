package com.kfeth.androidcleanarchitecture.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
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
                delay(1000)
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
    }

// Online only option. No DB caching used
inline fun <T> networkBoundResource(
    crossinline fetch: suspend () -> T,
    crossinline onFetchSuccess: () -> Unit = { },
    crossinline onFetchFailed: (Throwable) -> Unit = { },
) =
    channelFlow {
        val loading = launch {
            send(Resource.Loading<T>())
        }
        try {
            val result = fetch()
            onFetchSuccess()
            loading.cancel()
            delay(1000)
            send(Resource.Success(result))
        } catch (t: Throwable) {
            Timber.w(t)
            onFetchFailed(t)
            send(Resource.Error<T>(t))
        }
    }