package com.cocktailapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cocktailapp.R
import com.cocktailapp.adapter.DrinkAdapter
import com.cocktailapp.viewmodel.DrinkViewModel
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_drinks.*
import java.util.concurrent.TimeUnit.MILLISECONDS

class DrinksFragment : Fragment() {
    private lateinit var drinkAdapter: DrinkAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var viewModel: DrinkViewModel
    private lateinit var linearLayoutManager:LinearLayoutManager
    private var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_drinks, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(rv_drinks_fragment) {
            linearLayoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }
        viewModel = ViewModelProvider(this).get(DrinkViewModel::class.java)
        changeToolbar()
        with(arguments?.getStringArrayList("categories")) {
            if (this?.size == null) {
                toolbar.title = resources.getString(R.string.all_drinks)
                viewModel.getCategories()
            } else {
                when (this.size) {
                    1 -> toolbar.title = this[0]
                    2 -> toolbar.title = this[0] + ", " + this[1]
                    3 -> toolbar.title = this[0] + ", " + this[1] + ", " + this[2]
                    4 -> toolbar.title =
                        this[0] + ", " + this[1] + ", " + this[2] + ", " + this[3] + " ... "
                    else -> toolbar.title = resources.getString(R.string.drinks_by_selected)
                }
                viewModel.getAllDataByFilter(this)
            }
        }
        initObservers()
        rv_drinks_fragment.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                getPage()
            }
        })
    }

    private fun getPage() {
        isScrolling = true
        with(pb_load_drinks) {
            visibility = View.VISIBLE
            Observable.timer(3000, MILLISECONDS)
                .subscribe {
                if (::drinkAdapter.isInitialized && isScrolling) {
                    drinkAdapter.notifyDataSetChanged()
                }
                isScrolling = false
                visibility = View.GONE
            }.dispose()
        }
    }

    private fun changeToolbar() {
        toolbar = requireActivity().findViewById(R.id.toolbar)
        setHasOptionsMenu(true)
        toolbar.setNavigationOnClickListener {
            toolbar.navigationIcon = null
            findNavController().navigate(R.id.SecondFragment)
        }
    }

    private fun initObservers() = viewModel.apply {
        drinksListToFragment.observe(viewLifecycleOwner, {
            drinkAdapter = DrinkAdapter(it)
            rv_drinks_fragment.adapter = drinkAdapter
        })
    }
}