package com.cocktailapp.api


object Common {
    private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}