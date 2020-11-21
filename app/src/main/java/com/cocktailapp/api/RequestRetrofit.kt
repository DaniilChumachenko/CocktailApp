package com.cocktailapp.api

import com.cocktailapp.model.DrinksArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestRetrofit {
    @GET("filter.php")
    fun getDrinkTypeFilter(@Query("c") filter: String?): Call<DrinksArray>

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