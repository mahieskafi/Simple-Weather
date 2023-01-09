package com.eskafi.simple_weather.di

import android.app.Application
import com.eskafi.simple_weather.data.api.CurrentTimeApi
import com.eskafi.simple_weather.data.api.ForecastHourlyApi
import com.eskafi.simple_weather.data.repository.datasource.WeatherRemoteDataSource
import com.eskafi.simple_weather.data.repository.datasourceImpl.WeatherRemoteDataSourceImp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideWeatherRemoteDataSource(
        currentTimeApi: CurrentTimeApi,
        forecastHourlyApi: ForecastHourlyApi
    ): WeatherRemoteDataSource =
        WeatherRemoteDataSourceImp(
            currentTimeApi,
            forecastHourlyApi
        )

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }
}