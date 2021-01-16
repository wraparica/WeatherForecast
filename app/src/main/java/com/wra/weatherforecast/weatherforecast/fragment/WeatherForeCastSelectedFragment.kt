package com.wra.weatherforecast.weatherforecast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wra.weatherforecast.R
import com.wra.weatherforecast.databinding.FragmentSelectedBinding
import com.wra.weatherforecast.weatherforecast.viewmodel.WeatherForecastSelectedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherForeCastSelectedFragment : Fragment() {
    private val viewModel: WeatherForecastSelectedViewModel by viewModels()
    private lateinit var binding: FragmentSelectedBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI(){
        val bundle = arguments
        val id = bundle?.getLong("id")
        id?.let {
            viewModel.getDetails(it).observe(viewLifecycleOwner, Observer { item->
                binding.tvLocation.text = item.location
                val temp = item.temperature +"°C"
                binding.tvTemperature.text = temp
                binding.tvWeatherStatus.text = item.weatherStatus
                val tempRange = "High " + item.temperatureMax + "°C /" + " Low "  + item.temperatureMin +"°C"
                binding.tvTemperatureRange.text = tempRange

                if(item.favorite == 1){
                    binding.bFavorite.background = resources.getDrawable(R.drawable.ic_favorite_24, null)
                } else {
                    binding.bFavorite.background = resources.getDrawable(R.drawable.ic_favorite_border, null)
                }

                binding.bFavorite.setOnClickListener {
                    viewModel.setFavorite(item.favorite != 1, item.id
                    )
                }

            })
        }



    }
}