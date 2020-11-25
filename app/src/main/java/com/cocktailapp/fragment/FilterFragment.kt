package com.cocktailapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cocktailapp.R
import com.cocktailapp.adapter.FilterAdapter
import com.cocktailapp.viewmodel.FilterViewModel
import kotlinx.android.synthetic.main.fragment_filter.*

class FilterFragment : Fragment() {

    lateinit var filterAdapter: FilterAdapter
    private lateinit var viewModel: FilterViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_filter, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FilterViewModel::class.java)
        rv_filter_fragment.setHasFixedSize(true)
        rv_filter_fragment.layoutManager = LinearLayoutManager(context)
        initObservers()
        viewModel.getCategories()
        val bundle = Bundle()
        bt_apply_filter.setOnClickListener {
            bundle.putStringArrayList("categories", filterAdapter.selectedCategories)
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment,bundle )
        }
    }
    private fun initObservers() = viewModel.apply {
        categoriesListToFragment.observe(viewLifecycleOwner, {
            filterAdapter = FilterAdapter(it)
            filterAdapter.notifyDataSetChanged()
            rv_filter_fragment.adapter = filterAdapter
        })
    }
}