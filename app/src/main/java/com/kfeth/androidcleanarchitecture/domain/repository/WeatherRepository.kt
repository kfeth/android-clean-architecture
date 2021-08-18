package com.kfeth.androidcleanarchitecture.domain.repository

import com.kfeth.androidcleanarchitecture.domain.model.Result
import com.kfeth.androidcleanarchitecture.domain.model.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherForLocation(location: String): Result<WeatherInfo>
}