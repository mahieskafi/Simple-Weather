package com.eskafi.simple_weather.di

import com.eskafi.simple_weather.BuildConfig
import com.eskafi.simple_weather.data.api.CurrentTimeApi
import com.eskafi.simple_weather.data.api.ForecastHourlyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,
                        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrentTimeApi(retrofit: Retrofit) : CurrentTimeApi {
        return retrofit.create(CurrentTimeApi::class.java)
    }
    @Provides
    @Singleton
    fun provideForecastHourlyApi(retrofit: Retrofit) : ForecastHourlyApi {
        return retrofit.create(ForecastHourlyApi::class.java)
    }
}