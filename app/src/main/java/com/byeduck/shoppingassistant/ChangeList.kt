package com.byeduck.shoppingassistant

data class ChangeList<T>(
    val toBeModified: Map<Int, T>,
    val toBeAdded: List<T>,
    val toBeRemoved: List<Int>
)
