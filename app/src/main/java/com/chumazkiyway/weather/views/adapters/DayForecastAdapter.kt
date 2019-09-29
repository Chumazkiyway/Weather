package com.chumazkiyway.weather.views.adapters

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chumazkiyway.weather.R
import com.chumazkiyway.weather.databinding.RvItemDayForecastBinding
import com.chumazkiyway.weather.models.DayForecast

class DayForecastAdapter(private var items: List<DayForecast>,
                         private val itemClick: (Int) -> Unit,
                         private val context: Context
) : RecyclerView.Adapter<DayForecastAdapter.ViewHolder>() {

    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemDayForecastBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], itemClick)

    fun replaceData(list: List<DayForecast>) {
        items = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private var binding: RvItemDayForecastBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dayForecast: DayForecast, itemClick: (Int) -> Unit) {
            with(binding) {
                binding.dayForecast = dayForecast

                if ( selectedPosition == layoutPosition ) {
                    container.setBackgroundColor(ContextCompat.getColor(context, R.color.colorSelectedItem))
                    tvWeekDay.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                    tvTemperature.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                    ivWeatherCondition.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark),
                        android.graphics.PorterDuff.Mode.SRC_IN)
                } else {
                    container.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
                    tvWeekDay.setTextColor(ContextCompat.getColor(context, R.color.colorBlack))
                    tvTemperature.setTextColor(ContextCompat.getColor(context, R.color.colorBlack))
                    ivWeatherCondition.setColorFilter(ContextCompat.getColor(context, R.color.colorBlack),
                        android.graphics.PorterDuff.Mode.SRC_IN)
                }

                root.setOnClickListener {
                    itemClick.invoke(layoutPosition)
                    selectedPosition = layoutPosition
                    notifyDataSetChanged()
                }
                executePendingBindings()
            }
        }
    }
}
