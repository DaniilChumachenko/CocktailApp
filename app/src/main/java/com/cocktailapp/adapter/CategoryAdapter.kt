package com.cocktailapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cocktailapp.R
import com.cocktailapp.model.Drink

class CategoryAdapter(private val drinksArray: List<Drink>) :
    RecyclerView.Adapter<CategoryViewHolder>() {
    var selectedCategories = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false)
        return CategoryViewHolder(itemView, selectedCategories)
    }

    override fun getItemCount(): Int = drinksArray.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val listItem = drinksArray[position]
        holder.bind(listItem)
        holder.nameCategory.text = listItem.categoryDrink
    }
}