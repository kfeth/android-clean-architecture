package com.kfeth.androidcleanarchitecture.domain.interaction.weather

import com.kfeth.androidcleanarchitecture.domain.base.BaseUseCase
import com.kfeth.androidcleanarchitecture.domain.model.Result
import com.kfeth.androidcleanarchitecture.domain.model.WeatherInfo

interface GetWeatherUseCase : BaseUseCase<String, WeatherInfo> {

    override suspend operator fun invoke(location: String): Result<WeatherInfo>
}