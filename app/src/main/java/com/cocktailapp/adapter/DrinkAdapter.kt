package com.cocktailapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cocktailapp.R
import com.cocktailapp.model.DrinksArray
import com.squareup.picasso.Picasso

class DrinkAdapter(private val drinksArray: DrinksArray):RecyclerView.Adapter<DrinkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return DrinkViewHolder(itemView)
    }

    override fun getItemCount() = drinksArray.drinks.size

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val listItem = drinksArray.drinks[position]
        holder.bind(listItem)

        Picasso.get().load(drinksArray.drinks[position].strDrinkThumb).into(holder.drinkImage)
        holder.drinkName.text = drinksArray.drinks[position].strDrink
    }

}
