package com.cocktailapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cocktailapp.R
import com.cocktailapp.model.Drink


class DrinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val drinkImage: ImageView = itemView.findViewById(R.id.iv_drink_image)
    val drinkName: TextView = itemView.findViewById(R.id.tv_drink_name)

    fun bind(listItem: Drink) {
        drinkImage.setOnClickListener {
            Toast.makeText(
                it.context,
                "нажал на ${itemView.findViewById<ImageView>(R.id.iv_drink_image)}",
                Toast.LENGTH_SHORT
            )
                .show()
        }
        drinkName.setOnClickListener {
            Toast.makeText(
                it.context,
                "нажал на ${itemView.findViewById<TextView>(R.id.tv_drink_name)}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
