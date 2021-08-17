package com.kfeth.androidcleanarchitecture.domain.interaction.weather

import com.kfeth.androidcleanarchitecture.domain.repository.WeatherRepository

class GetWeatherUseCaseImpl(private val weatherRepository: WeatherRepository) : GetWeatherUseCase {

    override suspend operator fun invoke(location: String) =
        weatherRepository.getWeatherForLocation(location)
}