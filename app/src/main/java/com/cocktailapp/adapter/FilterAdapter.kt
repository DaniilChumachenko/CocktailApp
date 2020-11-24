package com.cocktailapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cocktailapp.R
import com.cocktailapp.model.Drink

class FilterAdapter(private val drinksArray: List<Drink>) :
    RecyclerView.Adapter<FilterViewHolder>() {
    var selectedCategories = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false)
        return FilterViewHolder(itemView, selectedCategories)
    }

    override fun getItemCount(): Int = drinksArray.size

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val listItem = drinksArray[position]
        holder.bind(listItem)
        holder.nameCategory.text = listItem.categoryDrink
    }
}