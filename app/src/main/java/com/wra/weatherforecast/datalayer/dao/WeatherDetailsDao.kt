package com.wra.weatherforecast.datalayer.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wra.weatherforecast.datalayer.entity.WeatherDetailsEntity


@Dao
abstract class WeatherDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun upsertMultiple(details: List<WeatherDetailsEntity>?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(detail: WeatherDetailsEntity?): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertMultiple(details: List<WeatherDetailsEntity>?)

    @Query("DELETE FROM weather_details")
    abstract fun delete()

    @Query("SELECT * FROM weather_details")
    abstract fun getWeatherDetails(): LiveData<List<WeatherDetailsEntity>>

    @Query("UPDATE weather_details set favorite = :favorite WHERE id = :id")
    abstract fun setFavorite(favorite: Long, id: Long)

    @Query("SELECT * FROM weather_details WHERE id = :id")
    abstract fun getDetails(id: Long): LiveData<WeatherDetailsEntity>

    @Query("SELECT * FROM weather_details WHERE id = :id")
    abstract fun getDetail(id: Long): WeatherDetailsEntity?
}