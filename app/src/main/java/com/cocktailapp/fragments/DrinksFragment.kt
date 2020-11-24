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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drinks, container, false)
    }
    var default = emptyList<Drink>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requestRetrofit = CommonRetrofit.requestRetrofitService
        rv_drinks_fragment.setHasFixedSize(true)
        rv_drinks_fragment.layoutManager = LinearLayoutManager(context)
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.navigationIcon = null
        setHasOptionsMenu(true)

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.SecondFragment)
        }
        with(arguments?.getStringArrayList("categories")) {
            getArray(this?:getCategoryies()).let {
                when (this?.size) {
                    1 -> toolbar.title = this[0]
                    2 -> toolbar.title = this[0] +", " + this[1]
                    3 -> toolbar.title = this[0] +", " + this[1] +", "+ this[2]
                    else -> toolbar.title = (this?.get(0) ?: "Drinks") +", " + (this?.get(1) ?: "Drinks") +", "+ (this?.get(2) ?: "Drinks")
                }
            }
        }
    }
    private fun getCategoryies(): ArrayList<String> {
        requestRetrofit.getAllCategories("list").enqueue(object : Callback<DrinksArray> {
            override fun onFailure(call: Call<DrinksArray>, t: Throwable) {
            }

            override fun onResponse(call: Call<DrinksArray>, response: Response<DrinksArray>) {
                default.plus(response.body()?.drinks as ArrayList<Drink>)
            }
        })
        var buf = ArrayList<String>()
        for (it in default)
            buf = buf.plus(it) as ArrayList<String>
        return buf
    }


    var finals = emptyList<Drink>()

    var flag: Boolean = false
    private fun getArray(stringArrayList: ArrayList<String>?) {

        if (stringArrayList != null) {
            for (it in stringArrayList) {
                Observable.zip(
                    requestRetrofit.getUser(it),
                    requestRetrofit.getUser("Ordinary_Drink")
                ) { c, s ->
                    return@zip mutableListOf(c)
                }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        add(it)
                    }
            }
            flag = true
        }
    }

    private fun add(list: MutableList<DrinksArray>) {
        for (it in list)
            finals = finals.plus(it.drinks!!)
        if (flag) {
            drinkAdapter = DrinkAdapter(finals)
            drinkAdapter.notifyDataSetChanged()
            rv_drinks_fragment.adapter = drinkAdapter
        }
    }
}