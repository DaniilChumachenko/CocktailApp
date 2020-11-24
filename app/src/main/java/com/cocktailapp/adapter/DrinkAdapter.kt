package com.cocktailapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cocktailapp.R
import com.cocktailapp.model.Drink
import com.squareup.picasso.Picasso

class DrinkAdapter(
    private val drinksArray: List<Drink>
) :
    RecyclerView.Adapter<DrinkViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.drink_layout, parent, false)
        return DrinkViewHolder(itemView)
    }

    override fun getItemCount(): Int = drinksArray.size

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = drinksArray[position]
        holder.bind(drink)
        with(drink) {
            Picasso.get().load(imageDrink).into(holder.drinkImage)
            holder.drinkName.text = nameDrink
        }

    }
}