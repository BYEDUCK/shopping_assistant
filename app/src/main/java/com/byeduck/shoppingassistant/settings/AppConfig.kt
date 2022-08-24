package com.byeduck.shoppingassistant.settings

data class AppConfig(
    val priceImportance: Int,
    val ratingImportance: Int,
    val brandImportance: Int,
    val aiImportance: Int,
    val trustedBrands: Set<String>,
    @Transient val topProductsCount: Int
)
