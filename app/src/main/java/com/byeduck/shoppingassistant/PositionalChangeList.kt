package com.byeduck.shoppingassistant

data class PositionalChangeList<T>(
    val toBeModified: Map<Int, T>,
    val toBeAdded: List<T>,
    val toBeRemoved: List<Int>
)
