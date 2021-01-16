package com.wra.weatherforecast.weatherforecast.validator

import android.content.Context
import androidx.lifecycle.LiveData
import com.wra.weatherforecast.datalayer.database.WfDb
import com.wra.weatherforecast.datalayer.entity.WeatherDetailsEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface WeatherForecastProvider {
    fun insert(items: List<WeatherDetailsEntity>)

    fun upsert(items: List<WeatherDetailsEntity>)

    fun getWeatherDetails() : LiveData<List<WeatherDetailsEntity>>

    fun setFavorite(favorite: Boolean, id: Long)

    fun getDetails(id: Long): LiveData<WeatherDetailsEntity>

    fun getDetail(id: Long): WeatherDetailsEntity?
}

class WeatherForecastProviderImpl @Inject constructor(
    @ApplicationContext ctx: Context
) : WeatherForecastProvider {
    private val db = WfDb.invoke(ctx)

    override fun insert(items: List<WeatherDetailsEntity>) {
        db.weatherDetailsDao().delete()
        return db.weatherDetailsDao().insertMultiple(items)
    }

    override fun upsert(items: List<WeatherDetailsEntity>) {
        return db.weatherDetailsDao().upsertMultiple(items)
    }

    override fun getWeatherDetails(): LiveData<List<WeatherDetailsEntity>> {
        return db.weatherDetailsDao().getWeatherDetails()
    }

    override fun setFavorite(favorite: Boolean, id: Long) {
        return db.weatherDetailsDao().setFavorite(if (favorite) 1 else 0, id)
    }

    override fun getDetails(id: Long): LiveData<WeatherDetailsEntity> {
        return db.weatherDetailsDao().getDetails(id)
    }

    override fun getDetail(id: Long): WeatherDetailsEntity? {
        return db.weatherDetailsDao().getDetail(id)
    }

}