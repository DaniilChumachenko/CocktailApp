package com.cocktailapp.api

import com.cocktailapp.model.DrinksArray
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestRetrofit {
    @GET("list.php")
    fun getAllCategories(@Query("c") filter: String?): Call<DrinksArray>

    @GET("filter.php")
    fun getDrinks(@Query("c") filter: String?): Observable<DrinksArray>
}