package com.kfeth.template.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    val isLoading: Boolean
        get() = this is Loading

    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null) : Resource<T>(data, throwable)
}

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Loading<T>(val data: T? = null) : Result<T>
    data class Error<T>(val throwable: Throwable? = null, val data: T? = null) : Result<T>
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> {
            Result.Success(it)
        }
        .onStart { emit(Result.Loading(null)) }
        .catch { emit(Result.Error(it)) }
}
