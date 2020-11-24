package com.cocktailapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cocktailapp.R
import com.cocktailapp.adapter.CategoryAdapter
import com.cocktailapp.api.CommonRetrofit
import com.cocktailapp.api.RequestRetrofit
import com.cocktailapp.model.Drink
import com.cocktailapp.model.DrinksArray
import kotlinx.android.synthetic.main.fragment_filter.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilterFragment : Fragment() {

    private lateinit var requestRetrofit: RequestRetrofit
    lateinit var categoryAdapter: CategoryAdapter

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
                Log.e(FilterFragment::class.java.simpleName,"getAllCategories request is failed")
            }

            override fun onResponse(call: Call<DrinksArray>, response: Response<DrinksArray>) {
                rv_filter_fragment.setHasFixedSize(true)
                rv_filter_fragment.layoutManager = LinearLayoutManager(context)
                categoryAdapter = CategoryAdapter(response.body()?.drinks as List<Drink>)
                categoryAdapter.notifyDataSetChanged()
                rv_filter_fragment.adapter = categoryAdapter
            }
        })
    }
}