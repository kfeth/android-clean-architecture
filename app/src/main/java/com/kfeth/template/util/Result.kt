package com.kfeth.template.util

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Loading<T>(val data: T? = null) : Result<T>
    data class Error<T>(val throwable: Throwable, val data: T? = null) : Result<T>
}
