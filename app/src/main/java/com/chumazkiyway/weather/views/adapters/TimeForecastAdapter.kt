package com.chumazkiyway.weather.views.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chumazkiyway.weather.databinding.RvItemTimeForecastBinding
import com.chumazkiyway.weather.models.TimeForecast

class TimeForecastAdapter(private var items: List<TimeForecast>
) : RecyclerView.Adapter<TimeForecastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemTimeForecastBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    class ViewHolder(private var binding: RvItemTimeForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(timeForecast: TimeForecast) {
            with(binding) {
                binding.timeForecast = timeForecast
                executePendingBindings()
            }
        }
    }
}