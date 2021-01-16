package com.wra.weatherforecast.datalayer.entity

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
@Entity(tableName = "weather_details")
data class WeatherDetailsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @Json(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "location")
    @Json(name = "location")
    var location: String = "",
    @ColumnInfo(name = "favorite")
    @Json(name = "favorite")
    var favorite: Int = 0,
    @ColumnInfo(name = "weather_status")
    @Json(name = "weather_status")
    var weatherStatus: String= "",
    @ColumnInfo(name = "temperature")
    @Json(name = "temperature")
    var temperature: String = "",
    @ColumnInfo(name = "temp_min")
    @Json(name = "temp_min")
    var temperatureMin: String = "",
    @ColumnInfo(name = "temp_max")
    @Json(name = "temp_max")
    var temperatureMax: String = ""
){
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WeatherDetailsEntity>() {
            override fun areItemsTheSame(
                oldItem: WeatherDetailsEntity,
                newItem: WeatherDetailsEntity
            ): Boolean {
                return oldItem.id == newItem.id
                        && oldItem.location == newItem.location
                        && oldItem.favorite == newItem.favorite
                        && oldItem.weatherStatus == newItem.weatherStatus
                        && oldItem.temperature == newItem.temperature
                        && oldItem.temperatureMin == newItem.temperatureMin
                        && oldItem.temperatureMax == newItem.temperatureMax
            }

            override fun areContentsTheSame(
                oldItem: WeatherDetailsEntity,
                newItem: WeatherDetailsEntity
            ): Boolean {
                return oldItem == newItem
                        && oldItem.location == newItem.location
                        && oldItem.favorite == newItem.favorite
                        && oldItem.weatherStatus == newItem.weatherStatus
                        && oldItem.temperature == newItem.temperature
                        && oldItem.temperatureMin == newItem.temperatureMin
                        && oldItem.temperatureMax == newItem.temperatureMax
            }

        }
    }
}