package com.cocktailapp.api


object CommonRetrofit {
    private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
    val requestRetrofitService: RequestRetrofit
        get() = ClientRetrofit.getClient(BASE_URL).create(RequestRetrofit::class.java)
}