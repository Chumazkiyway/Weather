package com.chumazkiyway.weather.views

import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.chumazkiyway.weather.R
import com.chumazkiyway.weather.databinding.ActivityMainBinding
import com.chumazkiyway.weather.viewmodels.MainActivityViewModel
import com.chumazkiyway.weather.views.adapters.DayForecastAdapter
import com.chumazkiyway.weather.views.adapters.TimeForecastAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    private val dayForecastAdapter: DayForecastAdapter by lazy {
        DayForecastAdapter(arrayListOf(), onDayClick, this)
    }

    private val timeForecastAdapter: TimeForecastAdapter by lazy {
        TimeForecastAdapter (arrayListOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel

        binding.rvDayForecast.layoutManager = LinearLayoutManager(this)
        binding.rvDayForecast.adapter = dayForecastAdapter

        binding.rvTimeForecast.layoutManager = LinearLayoutManager(this)
        binding.rvTimeForecast.adapter = timeForecastAdapter

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if( R.id.item_set_current_location == item?.itemId) {
            //TODO: handle item click event
        }
        return super.onOptionsItemSelected(item)
    }

    private val onDayClick: (Int) -> Unit = { position ->
        //TODO: Change day
    }
}

