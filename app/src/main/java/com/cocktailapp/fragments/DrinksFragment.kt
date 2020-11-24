package com.cocktailapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cocktailapp.R
import com.cocktailapp.adapter.DrinkAdapter
import com.cocktailapp.api.CommonRetrofit
import com.cocktailapp.api.RequestRetrofit
import com.cocktailapp.model.Drink
import com.cocktailapp.model.DrinksArray
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_drinks.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrinksFragment : Fragment() {
    lateinit var requestRetrofit: RequestRetrofit
    lateinit var drinkAdapter: DrinkAdapter
    lateinit var manager: LinearLayoutManager
    lateinit var toolbar: Toolbar
    var drinksList = emptyList<Drink>()
    var allCategories = emptyList<String>()
    var isReady: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drinks, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requestRetrofit = CommonRetrofit.requestRetrofitService
        manager = LinearLayoutManager(context)
        rv_drinks_fragment.setHasFixedSize(true)
        rv_drinks_fragment.layoutManager = manager
        toolbar = requireActivity().findViewById(R.id.toolbar)
        toolbar.navigationIcon = null
        setHasOptionsMenu(true)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.SecondFragment)
        }
        with(arguments?.getStringArrayList("categories")) {
            if (this?.size == null) {
                toolbar.title = "All Drinks"
                getCategoryies()
            } else {
                when (this.size) {
                    1 -> toolbar.title = this[0]
                    2 -> toolbar.title = this[0] + ", " + this[1]
                    3 -> toolbar.title = this[0] + ", " + this[1] + ", " + this[2]
                    4 -> toolbar.title =
                        this[0] + ", " + this[1] + ", " + this[2] + ", " + this[3] + " ... "
                    else -> toolbar.title = "Drinks by selected filters"
                }
                getAllDataByFilter(this)
            }
        }
    }

    private fun getCategoryies() {
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

    private fun getAllDataByFilter(stringArrayList: ArrayList<String>?) {
        if (stringArrayList != null) {
            for (it in stringArrayList) {
                Observable.zip(
                    requestRetrofit.getDrinks(it),
                    requestRetrofit.getDrinks("Ordinary_Drink")
                ) { categories, s ->
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
            drinkAdapter = DrinkAdapter(drinksList)
            drinkAdapter.notifyDataSetChanged()
            rv_drinks_fragment.adapter = drinkAdapter
        }
    }
}