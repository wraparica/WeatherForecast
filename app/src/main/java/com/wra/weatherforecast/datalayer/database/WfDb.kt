package com.wra.weatherforecast.datalayer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wra.weatherforecast.datalayer.dao.WeatherDetailsDao
import com.wra.weatherforecast.datalayer.entity.WeatherDetailsEntity

import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [
    WeatherDetailsEntity::class],
    version = 1)
abstract class WfDb : RoomDatabase() {
    abstract fun weatherDetailsDao(): WeatherDetailsDao

    companion object {

        private const val databaseName = "db-wf.db"
        private val databasePassword = "password".toCharArray()

        @Volatile
        private var instance: WfDb? = null

        operator fun invoke(context: Context) = instance ?: getNewInstance(context).also { instance = it }

        private fun getNewInstance(context: Context): WfDb {
            val factory = SupportFactory(SQLiteDatabase.getBytes(databasePassword))
            return Room.databaseBuilder(context, WfDb::class.java, databaseName)
                .allowMainThreadQueries()
                .openHelperFactory(factory)
                .build()
        }

        fun destroy() {
            instance?.let { if (it.isOpen) it.close() }
            instance = null
        }
    }
}