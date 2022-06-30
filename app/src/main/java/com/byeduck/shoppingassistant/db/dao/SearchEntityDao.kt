package com.byeduck.shoppingassistant.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.byeduck.shoppingassistant.db.SearchEntity

@Dao
interface SearchEntityDao {

    @Insert
    @Transaction
    fun insert(entity: SearchEntity)

    @Query("SELECT * FROM searches")
    fun getAll(): LiveData<List<SearchEntity>>
}