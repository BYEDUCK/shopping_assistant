package com.byeduck.shoppingassistant.products.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.byeduck.shoppingassistant.products.remote.Product

@Entity(tableName = "searches")
data class SearchEntity(
    var products: List<Product>,
    var query: String,
    var category: String
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}
