package com.cocktailapp.model

import com.google.gson.annotations.SerializedName

data class Drink (
    @SerializedName("strDrink")
    var nameDrink: String? = null,
    @SerializedName("strDrinkThumb")
    var imageDrink: String? = null,
    var idDrink: String? = null,
    @SerializedName("strCategory")
    var categoryDrink: String? = null
)