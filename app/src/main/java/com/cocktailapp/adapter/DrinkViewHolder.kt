package com.cocktailapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.cocktailapp.R
import com.cocktailapp.model.Drink


class DrinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val linearLayout: ConstraintLayout = itemView.findViewById(R.id.cl_item)
    val drinkImage: ImageView = itemView.findViewById(R.id.iv_drink_image)
    val drinkName: TextView = itemView.findViewById(R.id.tv_drink_name)

    fun bind(listItem: Drink) {
        linearLayout.setOnClickListener {
            Toast.makeText(it.context, listItem.nameDrink.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}