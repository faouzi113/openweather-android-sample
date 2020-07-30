package com.faouzibidi.weather.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.faouzibidi.weather.interactor.TownInteractor
import com.faouzibidi.weather.model.Town

class TownViewModel(application: Application) : AndroidViewModel(application) {

    private val towns =  MutableLiveData<List<Town>>()
    private val townInteractor = TownInteractor(application)

    fun getTowns(): LiveData<List<Town>> {
        return towns
    }

    private fun loadTowns() {
        towns.value = townInteractor.getTowns()
    }

}