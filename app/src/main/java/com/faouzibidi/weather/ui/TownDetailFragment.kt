package com.faouzibidi.weather.ui

import android.app.Activity
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.faouzibidi.weather.R
import com.faouzibidi.weatherlib.interactor.WeatherInteractor
import com.faouzibidi.weatherlib.model.Exclude
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class TownDetailFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_town_detail, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // get current location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Activity)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                // call Weather Interactor
                val weatherInteractor = WeatherInteractor(context)
                val weatherResult =  weatherInteractor.getWeatherInfo(location?.latitude.toString(),
                    location?.longitude.toString(), Exclude.daily, getString(R.string.app_id))
                // update ui
            }
    }

}