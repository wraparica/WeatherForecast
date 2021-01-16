package com.wra.weatherforecast.di

import android.content.Context
import com.wra.weatherforecast.datalayer.database.WfDb
import com.wra.weatherforecast.weatherforecast.validator.WeatherForecastProvider
import com.wra.weatherforecast.weatherforecast.validator.WeatherForecastProviderImpl
import com.wra.weatherforecast.weatherforecast.validator.WeatherForecastValidator
import com.wra.weatherforecast.weatherforecast.validator.WeatherForecastValidatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object WeatherForecastModule {

    @Singleton
    @Provides
    fun provideWeatherForecastProvider(@ApplicationContext ctx: Context): WeatherForecastProvider = WeatherForecastProviderImpl(ctx)

    @Singleton
    @Provides
    fun provideWeatherForecastValidator(@ApplicationContext ctx: Context): WeatherForecastValidator = WeatherForecastValidatorImpl(ctx)
}