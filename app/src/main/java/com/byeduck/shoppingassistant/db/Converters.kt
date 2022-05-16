package com.byeduck.shoppingassistant.db

import androidx.room.TypeConverter
import com.byeduck.shoppingassistant.products.remote.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun jsonToProducts(jsonProducts: String): List<Product> {
        return gson.fromJson(
            jsonProducts,
            TypeToken.getParameterized(List::class.java, Product::class.java).type
        )
    }

    @TypeConverter
    fun productsToJson(products: List<Product>): String {
        return gson.toJson(products)
    }

    @TypeConverter
    fun timestampToDate(timestamp: Long): ZonedDateTime {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.UTC)
    }

    @TypeConverter
    fun dateToTimestamp(dateTime: ZonedDateTime): Long {
        return dateTime.toInstant().toEpochMilli()
    }
}