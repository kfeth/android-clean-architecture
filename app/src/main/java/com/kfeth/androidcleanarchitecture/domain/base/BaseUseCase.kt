package com.kfeth.androidcleanarchitecture.domain.base

import com.kfeth.androidcleanarchitecture.domain.model.Result

interface BaseUseCase<T : Any, R: Any> {
    suspend operator fun invoke(param: T): Result<R>
}