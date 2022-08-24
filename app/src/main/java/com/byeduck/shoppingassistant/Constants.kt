package com.byeduck.shoppingassistant

import android.content.SharedPreferences
import com.byeduck.shoppingassistant.settings.AppConfig

const val PREF_FILE_NAME = "byeduck_sa_preferences"
const val PRICE_IMPORTANCE_PREF_NAME = "price_importance"
const val BRAND_IMPORTANCE_PREF_NAME = "brand_importance"
const val RATING_IMPORTANCE_PREF_NAME = "rating_importance"
const val AI_IMPORTANCE_PREF_NAME = "ai_importance"
const val TRUSTED_BRANDS_PREF_NAME = "trusted_brands"
const val TOP_PRODUCTS_COUNT = "top_products_count"
const val IMPORTANCE_DEFAULT_VALUE = 0
const val TOP_PRODUCTS_COUNT_DEFAULT_VALUE = 10
const val TRUSTED_BRANDS_SEPARATOR = ":"

fun parseTrustedBrands(brandText: String): Set<String> =
    if (brandText.isBlank()) emptySet() else brandText.split(TRUSTED_BRANDS_SEPARATOR).toSet()

fun getAppConfig(sharedPreferences: SharedPreferences): AppConfig = AppConfig(
    sharedPreferences.getInt(PRICE_IMPORTANCE_PREF_NAME, IMPORTANCE_DEFAULT_VALUE),
    sharedPreferences.getInt(RATING_IMPORTANCE_PREF_NAME, IMPORTANCE_DEFAULT_VALUE),
    sharedPreferences.getInt(BRAND_IMPORTANCE_PREF_NAME, IMPORTANCE_DEFAULT_VALUE),
    sharedPreferences.getInt(AI_IMPORTANCE_PREF_NAME, IMPORTANCE_DEFAULT_VALUE),
    parseTrustedBrands(
        sharedPreferences.getString(TRUSTED_BRANDS_PREF_NAME, "") ?: ""
    ),
    sharedPreferences.getInt(TOP_PRODUCTS_COUNT, TOP_PRODUCTS_COUNT_DEFAULT_VALUE)
)