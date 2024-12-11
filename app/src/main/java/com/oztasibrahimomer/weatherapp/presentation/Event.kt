package com.oztasibrahimomer.weatherapp.presentation

sealed class Event{

    data class GetStringWeather(val cityName:String) : Event()
}
