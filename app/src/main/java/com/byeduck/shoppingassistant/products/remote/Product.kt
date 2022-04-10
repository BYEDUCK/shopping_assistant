package com.byeduck.shoppingassistant.products.remote

import java.math.BigDecimal

data class Product(val name: String, val price: BigDecimal, val score: ProductScore)
