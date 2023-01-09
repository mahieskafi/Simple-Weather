package com.eskafi.simple_weather.di

import com.eskafi.simple_weather.data.location.LocationTrackerImpl
import com.eskafi.simple_weather.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun provideLocationTracker(
        defaultLocationTracker: LocationTrackerImpl
    ): LocationTracker
}