package com.byeduck.shoppingassistant.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.byeduck.shoppingassistant.products.remote.Product
import java.time.ZoneOffset
import java.time.ZonedDateTime

@Entity(tableName = "searches")
data class SearchEntity(
    var products: List<Product>,
    var query: String,
    var category: String,
    var date: ZonedDateTime = ZonedDateTime.now().withZoneSameLocal(ZoneOffset.UTC)
) : Identifiable {

    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0L
}
