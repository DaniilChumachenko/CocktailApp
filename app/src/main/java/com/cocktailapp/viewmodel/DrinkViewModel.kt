package com.cocktailapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cocktailapp.api.CommonRetrofit
import com.cocktailapp.api.RequestRetrofit
import com.cocktailapp.fragment.DrinksFragment
import com.cocktailapp.model.Drink
import com.cocktailapp.model.DrinksArray
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrinkViewModel : ViewModel() {

    private var requestRetrofit: RequestRetrofit = CommonRetrofit.requestRetrofitService

    private var drinksList = emptyList<Drink>()

    private var allCategories = emptyList<String>()

    private var _drinksListToFragment: MutableLiveData<List<Drink>> = MutableLiveData()

    val drinksListToFragment: LiveData<List<Drink>> = _drinksListToFragment

    private var isReady: Boolean = false

    fun getCategories() {
        requestRetrofit.getAllCategories("list").enqueue(object : Callback<DrinksArray> {
            override fun onFailure(call: Call<DrinksArray>, t: Throwable) {
                Log.e(DrinksFragment::class.java.simpleName, "getAllCategories request is failed")
            }

            override fun onResponse(call: Call<DrinksArray>, response: Response<DrinksArray>) {
                addAllDrinks(response.body()?.drinks as List<Drink>)
            }
        })
    }

    private fun addAllDrinks(list: List<Drink>?) {
        if (list != null) {
            for (it in list)
                allCategories = allCategories.plus(it.categoryDrink!!)
        }
        getAllDataByFilter(allCategories as ArrayList<String>)
    }

    fun getAllDataByFilter(stringArrayList: ArrayList<String>?) {
        if (stringArrayList != null) {
            for (it in stringArrayList) {
                Observable.zip(
                    requestRetrofit.getDrinks(it),
                    requestRetrofit.getDrinks("Ordinary_Drink")
                ) { categories, _ ->
                    return@zip mutableListOf(categories)
                }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        addDrinksToList(it)
                    }.isDisposed
            }
            isReady = true
        }
    }

    private fun addDrinksToList(list: MutableList<DrinksArray>) {
        for (it in list)
            drinksList = drinksList.plus(it.drinks!!)
        if (isReady) {
            _drinksListToFragment.value = drinksList
        }
    }
}