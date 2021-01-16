package com.wra.weatherforecast.weatherforecast.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.wra.weatherforecast.datalayer.entity.WeatherDetailsEntity
import com.wra.weatherforecast.weatherforecast.validator.WeatherForecastProvider
import com.wra.weatherforecast.weatherforecast.validator.WeatherForecastValidator

class WeatherForecastSelectedViewModel @ViewModelInject constructor(
    app: Application,
    private val provider: WeatherForecastProvider,
    private val validator: WeatherForecastValidator
) : AndroidViewModel(app) {

    fun setFavorite(favorite: Boolean, id: Long){
        provider.setFavorite(favorite, id)
    }

    fun getDetails(id: Long): LiveData<WeatherDetailsEntity>{
        return provider.getDetails(id)
    }
}