package com.kfeth.domain.interaction.weather

import com.kfeth.domain.base.BaseUseCase
import com.kfeth.domain.model.WeatherInfo

interface GetWeatherUseCase : BaseUseCase<String, WeatherInfo> {

    override suspend operator fun invoke(location: String): Result<WeatherInfo>
}