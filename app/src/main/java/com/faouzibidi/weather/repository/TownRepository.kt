package com.faouzibidi.weather.repository

import android.content.Context
import com.faouzibidi.weather.model.Town
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * this respository is used for storing towns information in a local file
 */
class TownRepository(val context: Context){
    val TOWN_KEY =  "town"
    val TOWN_FILE =  "town_file"

    /**
     * add new town to tha town list stored in local file
     */
    fun addTown(town: Town){
        val sharedPreferences = context.getSharedPreferences(TOWN_FILE, Context.MODE_PRIVATE)
        var townsStr = sharedPreferences.getString(TOWN_KEY, null)
        if (townsStr == null){
            // no town has been added so create new one
            val towns: List<Town>  = listOf(town)
            // parse list to a json string
            townsStr = Gson().toJson(towns)
            sharedPreferences.edit()
                .putString(TOWN_KEY, townsStr)
                .apply()
        }else{
            // we have some stored towns in the file
            // so we will add the given town to the list
            val gson = Gson()
            val townListType: Type = object : TypeToken<ArrayList<Town?>?>() {}.type
            val townList: List<Town> =
                gson.fromJson(townsStr, townListType)
            townList.plus(town)
            //
            sharedPreferences.edit()
                .putString(TOWN_KEY, gson.toJson(townList))
                .apply()
        }

    }

    /**
     * get a list of stored towns
     */
    fun getTownList():List<Town>{
        val sharedPreferences = context.getSharedPreferences(TOWN_FILE, Context.MODE_PRIVATE)
        val townsStr = sharedPreferences.getString(TOWN_KEY, null)
        val townListType: Type = object : TypeToken<ArrayList<Town?>?>() {}.type
        return Gson().fromJson(townsStr, townListType)
    }
}