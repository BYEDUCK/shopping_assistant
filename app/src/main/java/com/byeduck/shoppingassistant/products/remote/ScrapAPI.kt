package com.byeduck.shoppingassistant.products.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ScrapAPI {

    @GET("/api/scrap")
    fun getProducts(
        @Query("category") category: String,
        @Query("q") query: String,
        @Query("minPrice") minPrice: Int?,
        @Query("maxPrice") maxPrice: Int?
    ): Call<List<Product>>

    @GET("/api/scrap/categories")
    fun getCategories(): Call<List<String>>
}