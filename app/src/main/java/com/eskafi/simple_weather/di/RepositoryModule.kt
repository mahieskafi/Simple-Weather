package com.eskafi.simple_weather.di

import com.eskafi.simple_weather.data.repository.WeatherRepositoryImp
import com.eskafi.simple_weather.data.repository.datasource.WeatherRemoteDataSource
import com.eskafi.simple_weather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideProductRepository(
        dataSource: WeatherRemoteDataSource
    ): WeatherRepository =
        WeatherRepositoryImp(
            dataSource
        )
}