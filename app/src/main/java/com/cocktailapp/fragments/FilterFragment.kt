package com.cocktailapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cocktailapp.R
import com.cocktailapp.adapter.СategoryAdapter
import com.cocktailapp.api.CommonRetrofit
import com.cocktailapp.api.RequestRetrofit
import com.cocktailapp.model.Drink
import com.cocktailapp.model.DrinksArray
import kotlinx.android.synthetic.main.fragment_filter.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FilterFragment : Fragment() {

    lateinit var requestRetrofit: RequestRetrofit
    lateinit var categoryAdapter: СategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_filter, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requestRetrofit = CommonRetrofit.requestRetrofitService
        getCategoryies()
        val bundle = Bundle()
        bt_apply_filter.setOnClickListener {

            bundle.putStringArrayList("categories", categoryAdapter.selectedCategories)
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment,bundle )
        }
    }
    private fun getCategoryies() {
        requestRetrofit.getAllCategories("list").enqueue(object : Callback<DrinksArray> {
            override fun onFailure(call: Call<DrinksArray>, t: Throwable) {
            }

            override fun onResponse(call: Call<DrinksArray>, response: Response<DrinksArray>) {
                rv_filter_fragment.setHasFixedSize(true)
                rv_filter_fragment.layoutManager = LinearLayoutManager(context)
                categoryAdapter = СategoryAdapter(response.body()?.drinks as List<Drink>)
                categoryAdapter.notifyDataSetChanged()
                rv_filter_fragment.adapter = categoryAdapter
            }
        })
    }
}