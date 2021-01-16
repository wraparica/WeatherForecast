package com.wra.weatherforecast.weatherforecast.validator

import android.content.Context
import com.wra.weatherforecast.datalayer.database.WfDb
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface WeatherForecastValidator {
}

class WeatherForecastValidatorImpl @Inject constructor(
    @ApplicationContext ctx: Context
) : WeatherForecastValidator {

}