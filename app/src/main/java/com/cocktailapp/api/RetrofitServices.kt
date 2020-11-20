package com.cocktailapp.api

import com.cocktailapp.model.DrinksArray
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitServices {
    @GET("filter.php?c=Ordinary_Drink")
    fun getDrinksByOrder(): Call<DrinksArray>
//
//    @GET("filter.php?c=Ordinary_Drink")
//    fun getMovieList(): Call<DrinksArray>
//
//    @GET("filter.php?c=Ordinary_Drink")
//    fun getMovieList(): Call<DrinksArray>
//
//    @GET("filter.php?c=Ordinary_Drink")
//    fun getMovieList(): Call<DrinksArray>
//
//    @GET("filter.php?c=Ordinary_Drink")
//    fun getMovieList(): Call<DrinksArray>
}