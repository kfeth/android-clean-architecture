package com.kfeth.domain.repository

import com.kfeth.domain.model.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherForLocation(location: String): Result<WeatherInfo>
}