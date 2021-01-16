package com.wra.weatherforecast.weatherforecast.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wra.weatherforecast.R
import com.wra.weatherforecast.datalayer.entity.WeatherDetailsEntity

class WeatherForecastMainAdapter(private val listener: OnItemClickedListener, private val context: Context) :
    ListAdapter<WeatherDetailsEntity, WeatherForecastViewHolder>(WeatherDetailsEntity.DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder =
        WeatherForecastViewHolder.from(parent)

    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) =
        holder.bind(getItem(position), position, listener, context)

    interface OnItemClickedListener {
        fun onItemClicked(wde: WeatherDetailsEntity, item: Int)
    }

}

class WeatherForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvWeatherStatus: TextView = itemView.findViewById(R.id.tvWeatherStatus)
    private val tvTemperature: TextView = itemView.findViewById(R.id.tvTemperature)
    private val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
    private val bFavorite: Button = itemView.findViewById(R.id.bFavorite)
    private val llContainer: LinearLayout = itemView.findViewById(R.id.llContainer)

    fun bind(
        item: WeatherDetailsEntity?,
        pos: Int,
        listener: WeatherForecastMainAdapter.OnItemClickedListener,
        context: Context) {
        item ?: return

        if (item.favorite == 1){
            bFavorite.background = context.resources.getDrawable(R.drawable.ic_favorite_24, null)
        } else {
            bFavorite.background = context.resources.getDrawable(R.drawable.ic_favorite_border, null)
        }

        when {
            item.temperature.toDouble() < 0 -> llContainer.background = context.resources.getDrawable(R.color.freezing, null)
            item.temperature.toDouble() > 0 && item.temperature.toDouble() < 15 -> llContainer.background = context.resources.getDrawable(R.color.cold, null)
            item.temperature.toDouble() > 15 && item.temperature.toDouble() < 30 -> llContainer.background = context.resources.getDrawable(R.color.warm, null)
            item.temperature.toDouble() > 31 -> llContainer.background = context.resources.getDrawable(R.color.hot, null)
        }

        llContainer

        tvWeatherStatus.text = item.weatherStatus
        val temp = item.temperature +"°C"
        tvTemperature.text = temp
        tvLocation.text = item.location

        itemView.setOnClickListener { v: View? ->
            if (adapterPosition != RecyclerView.NO_POSITION) {
                listener.onItemClicked(item, pos)
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): WeatherForecastViewHolder =
            WeatherForecastViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.weather_item_list,
                    parent,
                    false
                )
            )
    }

}