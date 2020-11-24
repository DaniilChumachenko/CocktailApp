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
import kotlinx.android.synthetic.main.ordinary_drinks_fragment.*
import java.util.*

class DrinksFragment : Fragment() {
    lateinit var requestRetrofit: RequestRetrofit
    lateinit var drinkAdapter: DrinkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ordinary_drinks_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requestRetrofit = CommonRetrofit.requestRetrofitService
        rv_drinks_fragment.setHasFixedSize(true)
        rv_drinks_fragment.layoutManager = LinearLayoutManager(context)

        with(arguments?.getStringArrayList("categories")) {
            kotlin.run { filterBySelectCategory() }
        }
    }

    private fun ArrayList<String>?.filterBySelectCategory() {
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.navigationIcon = null
        setHasOptionsMenu(true)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.SecondFragment)
        }
        when (this?.size) {
            1 -> requestsWithFilter(
                this[0], "Ordinary_Drink",
                "Cocktail",
                "Milk / Float / Shake",
                "Beer",
                "Cocoa",
                "Shot",
                "Coffee / Tea",
                "Homemade_Liqueur", size
            ).let { toolbar.title = this[0] }
            2 -> requestsWithFilter(
                this[0], this[1],
                "Cocktail",
                "Milk / Float / Shake",
                "Beer",
                "Cocoa",
                "Shot",
                "Coffee / Tea",
                "Homemade_Liqueur", size
            ).let { toolbar.title = this[0] + ", " + this[1] }
            3 -> requestsWithFilter(
                this[0], this[1],
                this[2],
                "Milk / Float / Shake",
                "Beer",
                "Cocoa",
                "Shot",
                "Coffee / Tea",
                "Homemade_Liqueur", size
            ).let { toolbar.title = this[0] + ", " + this[1] + ", " + this[2] }
            4 -> requestsWithFilter(
                this[0], this[1],
                this[2],
                this[3],
                "Cocoa",
                "Cocoa",
                "Shot",
                "Coffee / Tea",
                "Homemade_Liqueur", size
            ).let { toolbar.title = this[0] + ", " + this[1] + ", " + this[2] + "..." }
            5 -> requestsWithFilter(
                this[0], this[1],
                this[2],
                this[3],
                this[4],
                "Cocoa",
                "Shot",
                "Coffee / Tea",
                "Homemade_Liqueur", size
            ).let { toolbar.title = this[0] + ", " + this[1] + ", " + this[2] + "..." }
            6 -> requestsWithFilter(
                this[0], this[1],
                this[2],
                this[3],
                this[4],
                this[5],
                "Shot",
                "Coffee / Tea",
                "Homemade_Liqueur", size
            ).let { toolbar.title = this[0] + ", " + this[1] + ", " + this[2] + "..." }
            7 -> requestsWithFilter(
                this[0], this[1],
                this[2],
                this[3],
                this[4],
                this[5],
                this[6],
                "Coffee / Tea",
                "Homemade_Liqueur", size
            ).let { toolbar.title = this[0] + ", " + this[1] + ", " + this[2] + "..." }
            8 -> requestsWithFilter(
                this[0], this[1],
                this[2],
                this[3],
                this[4],
                this[5],
                this[6],
                this[7],
                "Homemade_Liqueur", size
            ).let { toolbar.title = this[0] }
            9 -> requestsWithFilter(
                this[0], this[1],
                this[2],
                this[3],
                this[4],
                this[5],
                this[6],
                this[7],
                this[8], size
            ).let { toolbar.title = this[0] + ", " + this[1] + ", " + this[2] + "..." }
            else -> requestsWithFilter(
                "Ordinary_Drink",
                "Cocktail",
                "Milk / Float / Shake",
                "Beer",
                "Cocoa",
                "Shot",
                "Coffee / Tea",
                "Homemade_Liqueur",
                "Punch / Party Drink",
                9
            ).let { toolbar.title = "All drinks" }
        }
    }

    private fun requestsWithFilter(
        filter1: String,
        filter2: String,
        filter3: String,
        filter4: String,
        filter5: String,
        filter6: String,
        filter7: String,
        filter8: String,
        filter9: String,
        howManyRequests: Int
    ) {
        Observable.zip(
            requestRetrofit.getUser(filter1),
            requestRetrofit.getUser(filter2),
            requestRetrofit.getUser(filter3),
            requestRetrofit.getUser(filter4),
            requestRetrofit.getUser(filter5),
            requestRetrofit.getUser(filter6),
            requestRetrofit.getUser(filter7),
            requestRetrofit.getUser(filter8),
            requestRetrofit.getUser(filter9),
        ) { c, s, d, q, e, t, y, u, i ->
            when (howManyRequests) {
                1 -> return@zip mutableListOf(c)
                2 -> return@zip mutableListOf(c, s)
                3 -> return@zip mutableListOf(c, s, d)
                4 -> return@zip mutableListOf(c, s, d, q)
                5 -> return@zip mutableListOf(c, s, d, q, e)
                6 -> return@zip mutableListOf(c, s, d, q, e, t)
                7 -> return@zip mutableListOf(c, s, d, q, e, t, y)
                8 -> return@zip mutableListOf(c, s, d, q, e, t, y, u)
                9 -> return@zip mutableListOf(c, s, d, q, e, t, y, u, i)
                else -> return@zip mutableListOf(c)
            }

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapterInit(it)
            }
    }


    private fun adapterInit(it: MutableList<DrinksArray>) {
        drinkAdapter = DrinkAdapter(toArrayOfDrinks(it))
        drinkAdapter.notifyDataSetChanged()
        rv_drinks_fragment.adapter = drinkAdapter
    }


    private fun toArrayOfDrinks(mutableList: MutableList<DrinksArray>): List<Drink> {
        var finals: List<Drink> = emptyList()
        for (it in mutableList)
            finals = finals.plus(it.drinks!!)
        return finals
    }

}