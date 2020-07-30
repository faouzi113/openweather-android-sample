package com.faouzibidi.weatherlib.helpers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * this class check is used to check Internet connection
 */
class ConnectivityHelper {

    companion object {

        /**
         * this method check the device internet connection and returns a boolean which
         * signify that the device is connected to a network either on mobile data or wifi
         *
         * @return true if the device is connected to cellular or wifi network
         */
        fun isConnected(context: Context) : Boolean{
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork
            val networkCapabilities=connectivityManager.getNetworkCapabilities(network)
            return  networkCapabilities!=null &&
                    (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
        }
    }



}