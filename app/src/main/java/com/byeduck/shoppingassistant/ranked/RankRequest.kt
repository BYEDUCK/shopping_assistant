package com.byeduck.shoppingassistant.ranked

import com.byeduck.shoppingassistant.products.remote.Product
import com.byeduck.shoppingassistant.settings.AppConfig

data class RankRequest(val products: List<Product>, val config: AppConfig)
