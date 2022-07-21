package com.kfeth.template.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Loading<T>(val data: T? = null) : Result<T>
    data class Error<T>(val throwable: Throwable, val data: T? = null) : Result<T>
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
            saveFetchResponse(fetch())
            query().collect { emit(Result.Success(it)) }
        } catch (t: Throwable) {
            Timber.w(t)
            query().collect { emit(Result.Error(t, it)) }
        }
    }

inline fun <ResultType, RequestType> networkResult(
    crossinline fetch: suspend () -> RequestType,
    crossinline handleResponse: suspend (RequestType) -> ResultType,
) =
    flow {
        emit(Result.Loading<ResultType>(null))
        try {
            val data = handleResponse(fetch())
            emit(Result.Success(data))
        } catch (t: Throwable) {
            Timber.w(t)
            emit(Result.Error(t))
        }
    }

