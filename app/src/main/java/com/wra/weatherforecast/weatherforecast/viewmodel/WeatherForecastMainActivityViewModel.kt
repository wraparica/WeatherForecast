package com.wra.weatherforecast.weatherforecast.viewmodel

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import com.wra.weatherforecast.datalayer.entity.WeatherDetailsEntity
import com.wra.weatherforecast.weatherforecast.validator.WeatherForecastProvider
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class WeatherForecastMainActivityViewModel @ViewModelInject constructor(
    app: Application,
    private val provider: WeatherForecastProvider) : AndroidViewModel(app) {

    fun handleJson(jsonString: String?){
        val test = JSONObject(jsonString)
        val jArray: JSONArray = test.getJSONArray("list")
        val details: ArrayList<WeatherDetailsEntity> = ArrayList()
        for (i in 0 until jArray.length()) {
            val jsonObject = jArray.getJSONObject(i)
            val weather = jsonObject.getJSONArray("weather").getJSONObject(0)
            val weatherDescription = weather.getString("description")
            val main = jsonObject.getJSONObject("main")
            val temp = main.getString("temp")
            val tempMin = main.getString("temp_min")
            val tempMax = main.getString("temp_max")
            val location = jsonObject.getString("name")
            val detail = provider.getDetail(i.toLong() + 1)
            var favorite = 0
            detail?.let {
                favorite = detail.favorite
            }
            details.add(
                WeatherDetailsEntity(
                    id = i.toLong() + 1,
                    location = location,
                    favorite = favorite,
                    weatherStatus = weatherDescription,
                    temperature = temp,
                    temperatureMax = tempMax,
                    temperatureMin = tempMin
                )
            )
        }
        provider.upsert(details)
    }
}