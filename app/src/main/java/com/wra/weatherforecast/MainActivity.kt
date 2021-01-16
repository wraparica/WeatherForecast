package com.wra.weatherforecast

import android.Manifest
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import com.wra.weatherforecast.databinding.ActivityMainBinding
import com.wra.weatherforecast.util.constants.Urls
import com.wra.weatherforecast.weatherforecast.fragment.WeatherForeCastSelectedFragment
import com.wra.weatherforecast.weatherforecast.fragment.WeatherForecastMainFragment
import com.wra.weatherforecast.weatherforecast.viewmodel.WeatherForecastMainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks
import java.net.HttpURLConnection
import java.net.URL


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private val REQUEST_PERMISSION_CODE = 102
    private lateinit var viewModel: WeatherForecastMainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(WeatherForecastMainActivityViewModel::class.java)
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.permission_rationale),
            REQUEST_PERMISSION_CODE,
            Manifest.permission.INTERNET
        )
        checkNetwork()
        refreshApp()
        val fragment = WeatherForecastMainFragment()
        switchFragment(fragment, "list")

    }

    private fun checkNetwork(){
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer {
            if(it){
                checkPermission(Manifest.permission.INTERNET, "Internet", REQUEST_PERMISSION_CODE)
            } else {
                swipeToRefresh.isRefreshing = false
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onBackPressed() {
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.frameLayoutMainActivity)

        if (currentFragment is WeatherForeCastSelectedFragment){
            val fragment = WeatherForecastMainFragment()
            switchFragment(fragment, "list")
        }
    }

    private fun checkPermission(permission: String, name: String, requestCode: Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when {
                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED ->{
                    weatherForecast().execute(Urls.FETCH_WEATHER)
                }
                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_DENIED -> {
                    Toast.makeText(this, "Internet permission is required", Toast.LENGTH_SHORT).show()
                    finish()
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, requestCode)

                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        } else {
            weatherForecast().execute(Urls.FETCH_WEATHER)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck() {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Internet permission is required", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        innerCheck()
    }

    private fun showDialog(permission: String, name: String, requestCode: Int){
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK"){ dialog, which ->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun refreshApp() {
        swipeToRefresh.setOnRefreshListener {
            checkNetwork()
        }
    }

    inner class weatherForecast : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {
            var text: String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text = connection.inputStream.use {
                    it.reader().use { reader ->
                        reader.readText()
                    }
                }
            } finally {
                connection.disconnect()
            }

            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            viewModel.handleJson(result)
            swipeToRefresh.isRefreshing = false
        }
    }

    companion object

    fun switchFragment(
        fragment: Fragment,
        fragmentTag: String
    ) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayoutMainActivity, fragment, fragmentTag)
            .disallowAddToBackStack()
            .commitNow()
    }
}