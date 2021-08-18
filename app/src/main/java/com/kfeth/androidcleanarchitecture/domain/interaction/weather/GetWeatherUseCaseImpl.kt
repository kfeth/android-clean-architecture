package com.kfeth.androidcleanarchitecture.domain.interaction.weather

import com.kfeth.androidcleanarchitecture.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCaseImpl @Inject constructor(private val weatherRepository: WeatherRepository) :
    GetWeatherUseCase {

    override suspend operator fun invoke(location: String) =
        weatherRepository.getWeatherForLocation(location)
}