package com.byeduck.shoppingassistant.products.remote

import java.math.BigDecimal

data class Product(
    val name: String,
    val brandName: String,
    val url: String?,
    val price: BigDecimal,
    val reviewScore: ProductScore
)
