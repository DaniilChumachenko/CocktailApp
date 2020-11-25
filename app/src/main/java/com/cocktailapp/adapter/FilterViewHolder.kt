package com.cocktailapp.adapter

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.cocktailapp.R
import com.cocktailapp.model.Drink


class FilterViewHolder(itemView: View, var selectedCategories: ArrayList<String>) : RecyclerView.ViewHolder(itemView) {

    val nameCategory: TextView = itemView.findViewById(R.id.tv_category_name)
    private val statusOfSelect: ImageView = itemView.findViewById(R.id.iv_select_status)
    private val categoryDrinks: ConstraintLayout = itemView.findViewById(R.id.cl_category_field)

    fun bind(listItem: Drink) {
        categoryDrinks.setOnClickListener {
            handleClick(listItem)
        }
    }
    private fun handleClick(category: Drink) {
        with(statusOfSelect) {
            if (visibility == INVISIBLE) {
                visibility = VISIBLE
                category.categoryDrink?.let { selectedCategories.add(it) }
            } else {
                visibility = INVISIBLE
                selectedCategories.remove(category.categoryDrink)
            }
        }
    }
}