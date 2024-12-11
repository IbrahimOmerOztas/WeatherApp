package com.oztasibrahimomer.weatherapp.data.DependencyInjection

import com.oztasibrahimomer.weatherapp.data.repository.WeatherRepositoryImpl
import com.oztasibrahimomer.weatherapp.data.service.WeatherAPI
import com.oztasibrahimomer.weatherapp.domain.repository.WeatherRepository
import com.oztasibrahimomer.weatherapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideWeatherApi() : WeatherAPI {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api:WeatherAPI) : WeatherRepository{

        return WeatherRepositoryImpl(api=api)


    }


}