package com.eskafi.simple_weather.di

import com.eskafi.simple_weather.data.mapper.WeatherListResponseDtoMapper
import com.eskafi.simple_weather.data.mapper.WeatherResponseDtoMapper
import com.eskafi.simple_weather.domain.repository.WeatherRepository
import com.eskafi.simple_weather.domain.usecase.currentweather.GetCurrentTimeWeatherUseCase
import com.eskafi.simple_weather.domain.usecase.currentweather.GetCurrentTimeWeatherUseCaseImp
import com.eskafi.simple_weather.domain.usecase.forecasthourly.GetForecastHourlyUseCase
import com.eskafi.simple_weather.domain.usecase.forecasthourly.GetForecastHourlyUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCurrentTimeWeatherUseCase(
        weatherRepository: WeatherRepository,
        weatherResponseDtoMapper: WeatherResponseDtoMapper
    ): GetCurrentTimeWeatherUseCase =
        GetCurrentTimeWeatherUseCaseImp(
            weatherRepository,
            weatherResponseDtoMapper
        )

    @Provides
    fun provideGetForecastHourlyListUseCase(
        weatherRepository: WeatherRepository,
        weatherListResponseDtoMapper: WeatherListResponseDtoMapper
    ): GetForecastHourlyUseCase =
        GetForecastHourlyUseCaseImp(
            weatherRepository,
            weatherListResponseDtoMapper
        )

    @Provides
    fun provideWeatherResponseDtoMapper() = WeatherResponseDtoMapper()

    @Provides
    fun provideWeatherListResponseDtoMapper() = WeatherListResponseDtoMapper()
}