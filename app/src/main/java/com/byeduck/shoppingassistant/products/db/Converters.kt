package com.byeduck.shoppingassistant.products.db

import androidx.room.TypeConverter
import com.byeduck.shoppingassistant.products.remote.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun jsonToProducts(jsonProducts: String): List<Product> {
        return gson.fromJson(
            jsonProducts,
            TypeToken.getParameterized(List::class.java, Product::class.java) as Type
        )
    }

    @TypeConverter
    fun productsToJson(products: List<Product>): String {
        return gson.toJson(products)
    }
}