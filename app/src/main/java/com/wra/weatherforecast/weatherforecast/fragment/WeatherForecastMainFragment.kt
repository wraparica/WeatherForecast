package com.wra.weatherforecast.weatherforecast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wra.weatherforecast.MainActivity
import com.wra.weatherforecast.databinding.FragmentMainBinding
import com.wra.weatherforecast.datalayer.entity.WeatherDetailsEntity
import com.wra.weatherforecast.weatherforecast.adapter.WeatherForecastMainAdapter
import com.wra.weatherforecast.weatherforecast.viewmodel.WeatherForecastViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class WeatherForecastMainFragment : Fragment(), WeatherForecastMainAdapter.OnItemClickedListener {
    private val viewModel: WeatherForecastViewModel by viewModels()
    private lateinit var adapter: WeatherForecastMainAdapter
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI(){
        adapter = WeatherForecastMainAdapter(this,
            requireContext())
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItems.adapter = adapter

        viewModel.getWeatherDetails().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

    }


    override fun onItemClicked(wde: WeatherDetailsEntity, item: Int) {
        val mainActivity = (activity as MainActivity)
        val args = Bundle()
        args.putLong("id", wde.id)
        val fragment = WeatherForeCastSelectedFragment()
        fragment.arguments = args
        mainActivity.switchFragment(fragment, "selected")
    }
}