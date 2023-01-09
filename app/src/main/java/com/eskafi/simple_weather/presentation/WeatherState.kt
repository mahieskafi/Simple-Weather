package com.eskafi.simple_weather.presentation

sealed class WeatherState<out R> {
    class ShowData<out T>(val data: T) : WeatherState<T>()
    class ShowError(val message: String) : WeatherState<Nothing>()
    object IsLoading : WeatherState<Nothing>()
}
