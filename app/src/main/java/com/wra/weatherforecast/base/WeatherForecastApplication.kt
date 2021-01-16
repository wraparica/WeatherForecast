package com.wra.weatherforecast.base

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.Keep

import dagger.hilt.android.HiltAndroidApp
import java.util.*
import javax.inject.Inject

@Keep
@HiltAndroidApp
class WeatherForecastApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        app = this

    }

    companion object {
        var app: WeatherForecastApplication? = null
            private set
        val appContext: Context
            get() = app!!.applicationContext

    }
}