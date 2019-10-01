package com.chumazkiyway.weather.views

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.chumazkiyway.weather.R
import com.chumazkiyway.weather.viewmodels.MapsActivityViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*



class MapsActivity : AppCompatActivity(), OnMapReadyCallback , GoogleMap.OnMapClickListener {

    private lateinit var map: GoogleMap

    private val viewModel: MapsActivityViewModel by lazy {
        ViewModelProviders.of(this).get(MapsActivityViewModel::class.java)
    }

    companion object {
        const val LAT = "LAT"
        const val LNG = "LNG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fab.setOnClickListener {
            val intent = Intent()
            intent.putExtra(LAT, viewModel.tmpLatLng.latitude )
            intent.putExtra(LNG, viewModel.tmpLatLng.longitude)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val defaultLocation = LatLng(viewModel.location.lat, viewModel.location.lng)
        map.addMarker(MarkerOptions().position(defaultLocation))
        map.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation))
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMapClickListener(this)
        val cameraPosition = CameraPosition.Builder().target(defaultLocation).zoom(14.0f).build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        map.moveCamera(cameraUpdate)
        map.uiSettings.isZoomControlsEnabled = false
    }

    override fun onMapClick(latLng: LatLng?) {
        latLng?.let{
            viewModel.onMapClick(latLng)
            map.clear()
            map.addMarker(MarkerOptions().position(latLng))
        }
    }
}
