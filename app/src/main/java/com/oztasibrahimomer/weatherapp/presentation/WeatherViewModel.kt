package com.oztasibrahimomer.weatherapp.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oztasibrahimomer.weatherapp.domain.useCase.GetWeatherUseCase
import com.oztasibrahimomer.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCase: GetWeatherUseCase)
    :ViewModel() {

    private val _state = mutableStateOf(WeatherState())
    val state:State<WeatherState> = _state





    fun getWeather(city:String) {

        weatherUseCase.executeGetWeather(city=city).onEach {

            when(it){
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = it.message.toString(),
                        dto = null
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(

                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "",
                        dto = it.data?.body()
                    )
                }
            }

        }.launchIn(viewModelScope)
    }


    fun onEvent(event: Event) {

        when(event){
            is Event.GetStringWeather -> {
                getWeather(event.cityName)
            }
        }

    }





}