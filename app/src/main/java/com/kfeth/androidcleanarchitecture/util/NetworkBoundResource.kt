package com.kfeth.androidcleanarchitecture.util

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline onFetchSuccess: () -> Unit = { },
    crossinline onFetchFailed: (Throwable) -> Unit = { }
) = channelFlow {
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
            Log.e("NetworkBoundResource", "$t")
            onFetchFailed(t)
            loading.cancel()
            query().collect { send(Resource.Error(t, it)) }
        }
    } else {
        query().collect { send(Resource.Success(it)) }
    }
}

/*

inline fun <RequestType> networkBoundResourceOnline(
    crossinline fetch: suspend () -> Response<RequestType>,
    crossinline onFetchFailed: (Throwable) -> Unit = { },
) =
    flow {
        emit(Resource.Loading(null))
        try {
            val response = fetch()
            if (!response.isSuccessful) {
                throw Exception("Error: ${response.code()} ${response.message()}")
            }
            emit(Resource.Success(response.body()))
        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            emit(Resource.Error(throwable, null))
        }
    }

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> Response<RequestType>,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { },
    crossinline shouldFetch: (ResultType?) -> Boolean = { true },
) =
    flow {
        val data = query().first()
        val flow = if (shouldFetch(data)) {
            emit(Resource.Loading(data))

            try {
                val response = fetch()
                if (!response.isSuccessful) {
                    throw Exception("Error: ${response.code()} ${response.message()}")
                }
                saveFetchResult((response.body()!!))
                query().map { Resource.Success(it) }
            } catch (throwable: Throwable) {
                onFetchFailed(throwable)
                query().map { Resource.Error(throwable, it) }
            }
        } else {
            query().map { Resource.Success(it) }
        }
        emitAll(flow)
    }

 */