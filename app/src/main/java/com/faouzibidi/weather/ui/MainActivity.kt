package com.faouzibidi.weather.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.faouzibidi.weather.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.container, TownListFragment())
            .commit()
    }

    fun currentLocation(view: View) {
        supportFragmentManager.beginTransaction().add(R.id.container, TownDetailFragment())
            .commit()
    }
}