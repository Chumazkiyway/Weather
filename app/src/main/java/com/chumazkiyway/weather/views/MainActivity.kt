package com.chumazkiyway.weather.views

import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chumazkiyway.weather.R
import com.chumazkiyway.weather.databinding.ActivityMainBinding
import com.chumazkiyway.weather.models.DayForecast
import com.chumazkiyway.weather.models.TimeForecast
import com.chumazkiyway.weather.viewmodels.MainActivityViewModel
import com.chumazkiyway.weather.viewmodels.MainActivityViewModelFactory
import com.chumazkiyway.weather.views.adapters.DayForecastAdapter
import com.chumazkiyway.weather.views.adapters.TimeForecastAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.content.pm.PackageManager
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.libraries.places.api.Places
import android.content.Intent
import android.app.Activity
import com.chumazkiyway.weather.views.MapsActivity.Companion.LAT
import com.chumazkiyway.weather.views.MapsActivity.Companion.LNG


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val placesClient by lazy { Places.createClient(this) }

    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProviders.of(this, MainActivityViewModelFactory(resources.configuration.locale, this))
            .get(MainActivityViewModel::class.java)
    }

    private val dayForecastAdapter: DayForecastAdapter by lazy {
        DayForecastAdapter(arrayListOf(), onDayClick, this)
    }

    private val timeForecastAdapter: TimeForecastAdapter by lazy {
        TimeForecastAdapter (arrayListOf())
    }

    companion object {
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        const val MAPS_REQUEST_CODE = 2
        const val AUTOCOMPLETE_REQUEST_CODE = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel

        binding.rvDayForecast.layoutManager = LinearLayoutManager(this)
        binding.rvDayForecast.adapter = dayForecastAdapter
        binding.rvTimeForecast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                                                        false)
        binding.rvTimeForecast.adapter = timeForecastAdapter

        viewModel.dailyForecastList.observe(this, Observer<List<DayForecast>>{
            it?.let { items -> dayForecastAdapter.replaceData(items) }
        })

        viewModel.timeForecastList.observe(this, Observer<List<TimeForecast>>{
            it?.let { items -> timeForecastAdapter.replaceData(items) }
        })

        viewModel.selectedDay.observe(this, Observer<DayForecast>{
            it?.let { dayForecast -> binding.weatherDetails.details = dayForecast }
        })

        viewModel.cityName.observe(this, Observer<String>{
            it?.let { cityName -> binding.tvCurrentLocation.text = cityName}
        })

        iv_set_location.setOnClickListener {
            val i = Intent(this@MainActivity, MapsActivity::class.java)
            startActivityForResult(i, MAPS_REQUEST_CODE)
        }

        tv_current_location.setOnClickListener {
            val i = Intent(this@MainActivity, SearchActivity::class.java)
            startActivityForResult(i, AUTOCOMPLETE_REQUEST_CODE)
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        Places.initialize(this, getString(R.string.places_api_key))
        getCurrentLocation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_current_location, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if( R.id.item_set_current_location == item?.itemId) {
            getCurrentLocation()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    viewModel.getCurrentLocation(placesClient)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            MAPS_REQUEST_CODE -> {
                if(resultCode == Activity.RESULT_OK){
                    data.let {
                        val lat = it!!.getDoubleExtra(LAT,0.0)
                        val lng = it.getDoubleExtra(LNG,0.0)

                        if(lat != 0.0 && lng != 0.0) {
                            viewModel.onPickPlace(lat, lng)
                        }
                    }
                }
            }

        }
    }

    private val onDayClick: (Int) -> Unit = { position ->
        viewModel.getTimeForecast(position)
    }

    private fun getCurrentLocation () {
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            viewModel.getCurrentLocation(placesClient)
        } else {
            ActivityCompat.requestPermissions(this,
                Array(1){ACCESS_FINE_LOCATION},
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }
}

