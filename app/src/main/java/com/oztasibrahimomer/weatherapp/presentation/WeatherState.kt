package com.oztasibrahimomer.weatherapp.presentation

import com.oztasibrahimomer.weatherapp.data.dto.WeatherDTO

data class WeatherState(

    val isLoading:Boolean=false,
    val error:String="",
    val dto:WeatherDTO?=null
)
