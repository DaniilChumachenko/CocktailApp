package com.cocktailapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cocktailapp.R
import com.cocktailapp.adapter.DrinkAdapter
import com.cocktailapp.viewmodel.DrinkViewModel
import kotlinx.android.synthetic.main.fragment_drinks.*

class DrinksFragment : Fragment() {
    private lateinit var drinkAdapter: DrinkAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var viewModel: DrinkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drinks, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(rv_drinks_fragment) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
        viewModel = ViewModelProvider(this).get(DrinkViewModel::class.java)
        changeToolbar()
        initObservers()
        with(arguments?.getStringArrayList("categories")) {
            if (this?.size == null) {
                toolbar.title = "All Drinks"
                viewModel.getCategories()
            } else {
                when (this.size) {
                    1 -> toolbar.title = this[0]
                    2 -> toolbar.title = this[0] + ", " + this[1]
                    3 -> toolbar.title = this[0] + ", " + this[1] + ", " + this[2]
                    4 -> toolbar.title =
                        this[0] + ", " + this[1] + ", " + this[2] + ", " + this[3] + " ... "
                    else -> toolbar.title = "Drinks by selected filters"
                }
                viewModel.getAllDataByFilter(this)
            }
        }
    }

    private fun changeToolbar() {
        toolbar = requireActivity().findViewById(R.id.toolbar)
        toolbar.navigationIcon = null
        setHasOptionsMenu(true)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.SecondFragment)
        }
    }

    private fun initObservers() = viewModel.apply {
        drinksListToFragment.observe(viewLifecycleOwner, {
            drinkAdapter = DrinkAdapter(it)
            drinkAdapter.notifyDataSetChanged()
            rv_drinks_fragment.adapter = drinkAdapter
        })
    }
}