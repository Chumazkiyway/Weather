package com.chumazkiyway.weather.views.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.chumazkiyway.weather.R
import kotlinx.android.synthetic.main.activity_search.*
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.chumazkiyway.weather.viewmodels.SearchActivityViewModel
import com.google.android.libraries.places.api.Places


class SearchActivity: AppCompatActivity() {

    private val adapter by lazy {
        ArrayAdapter(this, R.layout.lv_item_location, arrayListOf(""))
    }

    private val placesClient by lazy { Places.createClient(this) }

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(SearchActivityViewModel::class.java)
    }

    companion object {
        const val SELECTED_LOCATION = "SELECTED_LOCATION "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        sv_search_location.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.getLocations(placesClient, it) }
                return false
            }

        })

        location_list.adapter = adapter
        location_list.divider = null

        location_list.setOnItemClickListener { _, _, i, _ ->
            val intent = Intent()
            intent.putExtra(SELECTED_LOCATION, adapter.getItem(i))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        viewModel.predictions.observe(this, Observer {
            it?.let {predictions ->
                adapter.clear()
                adapter.addAll(predictions)
                adapter.notifyDataSetChanged()
            }
        })

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}