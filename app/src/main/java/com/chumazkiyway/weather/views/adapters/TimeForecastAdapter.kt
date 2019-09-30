package com.chumazkiyway.weather.views.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
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

    fun replaceData(list: List<TimeForecast>) {
        items = list
        notifyDataSetChanged()
    }

    class ViewHolder(private var binding: RvItemTimeForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(timeForecast: TimeForecast) {
            binding.timeForecast = timeForecast
            binding.executePendingBindings()
        }
    }
}