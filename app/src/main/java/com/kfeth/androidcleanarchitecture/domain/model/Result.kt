package com.kfeth.androidcleanarchitecture.domain.model

sealed class Result<out T : Any>
data class Success<out T : Any>(val data: T) : Result<T>()
data class Failure(val httpError: HttpError) : Result<Nothing>()

class HttpError(val throwable: Throwable, val errorCode: Int = 0)