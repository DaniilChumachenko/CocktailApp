package com.cocktailapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cocktailapp.api.CommonRetrofit
import com.cocktailapp.api.RequestRetrofit
import com.cocktailapp.fragment.FilterFragment
import com.cocktailapp.model.Drink
import com.cocktailapp.model.DrinksArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilterViewModel: ViewModel() {
    private var requestRetrofit: RequestRetrofit = CommonRetrofit.requestRetrofitService

    private var _categoriesListToFragment: MutableLiveData<List<Drink>> = MutableLiveData()

    val categoriesListToFragment: LiveData<List<Drink>> = _categoriesListToFragment

    fun getCategories() {
        requestRetrofit.getAllCategories("list").enqueue(object : Callback<DrinksArray> {
            override fun onFailure(call: Call<DrinksArray>, t: Throwable) {
                Log.e(FilterFragment::class.java.simpleName,"getAllCategories request is failed")
            }

            override fun onResponse(call: Call<DrinksArray>, response: Response<DrinksArray>) {
                _categoriesListToFragment.value = response.body()?.drinks as List<Drink>
            }
        })
    }

}