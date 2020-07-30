package com.faouzibidi.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faouzibidi.weather.R
import com.faouzibidi.weather.ui.adapter.TownRecyclerViewAdapter
import com.faouzibidi.weather.viewmodel.TownViewModel

/**
 * A fragment representing a list of Towns.
 */
class TownListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_town_list_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager =  LinearLayoutManager(context)
                val model = ViewModelProvider(activity!!).get(TownViewModel::class.java)
                model.getTowns().observe(viewLifecycleOwner, Observer {towns -> adapter =
                    TownRecyclerViewAdapter(towns)
                })

            }
        }
        return view
    }
}