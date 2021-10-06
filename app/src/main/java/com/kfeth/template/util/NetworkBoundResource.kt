package com.kfeth.template.util

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
                query().collect { send(UiState.Loading(it)) }
            }
            try {
                saveFetchResult(fetch())
                onFetchSuccess()
                loading.cancel()
                delay(1000)
                query().collect { send(UiState.Success(it)) }
            } catch (t: Throwable) {
                Timber.w(t)
                onFetchFailed(t)
                loading.cancel()
                query().collect { send(UiState.Error(t, it)) }
            }
        } else {
            query().collect { send(UiState.Success(it)) }
        }
    }
