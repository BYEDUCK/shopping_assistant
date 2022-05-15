package com.byeduck.shoppingassistant.products.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.byeduck.shoppingassistant.products.db.SearchEntity

@Dao
interface SearchEntityDao {

    @Insert
    suspend fun insert(entity: SearchEntity): Long
}