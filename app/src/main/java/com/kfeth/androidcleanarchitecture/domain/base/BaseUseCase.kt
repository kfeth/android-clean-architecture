package com.kfeth.androidcleanarchitecture.domain.base

interface BaseUseCase<T : Any, R: Any> {

    suspend operator fun invoke(param: T): Result<R>
}