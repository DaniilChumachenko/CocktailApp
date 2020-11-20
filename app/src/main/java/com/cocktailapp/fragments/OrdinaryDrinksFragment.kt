package com.cocktailapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cocktailapp.R
import com.cocktailapp.adapter.DrinkAdapter
import com.cocktailapp.api.Common
import com.cocktailapp.api.RetrofitServices
import com.cocktailapp.model.DrinksArray
import com.cocktailapp.viewmodels.OrdinaryDrinksViewModel
import kotlinx.android.synthetic.main.ordinary_drinks_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdinaryDrinksFragment : Fragment() {

    lateinit var mService: RetrofitServices
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: DrinkAdapter
    private lateinit var viewModel: OrdinaryDrinksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ordinary_drinks_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrdinaryDrinksViewModel::class.java)

        mService = Common.retrofitService
        recyclerMovieList.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        recyclerMovieList.layoutManager = layoutManager

        getAllMovieList()
    }

    private fun getAllMovieList() {
        mService.getDrinksByOrder().enqueue(object : Callback<DrinksArray> {
            override fun onFailure(call: Call<DrinksArray>, t: Throwable) {
            }
            override fun onResponse(call: Call<DrinksArray>, response: Response<DrinksArray>) {
                adapter = DrinkAdapter(response.body() as DrinksArray)
                adapter.notifyDataSetChanged()
                recyclerMovieList.adapter = adapter
            }
        })
    }
}