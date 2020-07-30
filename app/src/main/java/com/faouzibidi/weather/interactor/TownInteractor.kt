package com.faouzibidi.weather.interactor

import android.content.Context
import com.faouzibidi.weather.model.Town
import com.faouzibidi.weather.repository.TownRepository

class TownInteractor(val context: Context) {

    val townRepository = TownRepository(context)

    fun getTowns() : List<Town>{
        return townRepository.getTownList()
    }

}