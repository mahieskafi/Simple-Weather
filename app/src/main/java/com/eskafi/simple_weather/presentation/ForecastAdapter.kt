package com.eskafi.simple_weather.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eskafi.simple_weather.BuildConfig
import com.eskafi.simple_weather.databinding.ItemForecastBinding
import com.eskafi.simple_weather.domain.model.WeatherResponse
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class ForecastAdapter : ListAdapter<WeatherResponse, RecyclerView.ViewHolder>(
    WeatherDiffCallBack()
) {

    private class WeatherDiffCallBack : DiffUtil.ItemCallback<WeatherResponse>() {
        override fun areItemsTheSame(oldItem: WeatherResponse, newItem: WeatherResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: WeatherResponse,
            newItem: WeatherResponse
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ForecastItemViewHolder(
            ItemForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is ForecastItemViewHolder)
            holder.bind(item)
    }

    class ForecastItemViewHolder(
        private val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(forecastItem: WeatherResponse) {

            binding.tempTextView.text = java.lang.String.format(
                Locale.getDefault(),
                "%.0f°",
                forecastItem.temperatureInfo.temperature
            )

            val url = BuildConfig.IMAGE_URL +
                    forecastItem.mainInfo[0].icon +
                    "@2x.png"
            Glide
                .with(binding.root)
                .load(
                    url
                )
                .dontAnimate()
                .centerInside()
                .into(binding.weatherImageView)


            val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())
            val netDate = Date(forecastItem.dateTime * 1000)
            binding.dayNameTextView.text = sdf.format(netDate).split(" ")[1]

            binding.maxTempTextView.text = forecastItem.temperatureInfo.maxTemperature.toString()
            binding.minTempTextView.text = forecastItem.temperatureInfo.minTemperature.toString()
        }
    }
}