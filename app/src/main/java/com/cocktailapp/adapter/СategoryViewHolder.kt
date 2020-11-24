package com.cocktailapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.cocktailapp.R
import com.cocktailapp.model.Drink


class СategoryViewHolder(itemView: View, var selectedCategories: ArrayList<String>) : RecyclerView.ViewHolder(itemView) {

    val nameCategory: TextView = itemView.findViewById(R.id.tv_category_name)
    val statusOfSelect: ImageView = itemView.findViewById(R.id.iv_select_status)
    val categoryDrinks: ConstraintLayout = itemView.findViewById(R.id.cl_category_field)

    fun bind(listItem: Drink) {
        categoryDrinks.setOnClickListener {
            handleClick(listItem, itemView)
        }
    }
    private fun handleClick(category: Drink, itemView: View) {
        with(statusOfSelect) {
            if (visibility == View.INVISIBLE) {
                visibility = View.VISIBLE
                category.categoryDrink?.let { selectedCategories.add(it) }
            } else {
                visibility = View.INVISIBLE
                selectedCategories.remove(category)
            }
        }
    }
}
