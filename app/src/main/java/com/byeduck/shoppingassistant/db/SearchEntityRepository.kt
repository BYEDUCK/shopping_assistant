package com.byeduck.shoppingassistant.db

import com.byeduck.shoppingassistant.db.dao.SearchEntityDao

class SearchEntityRepository(private val searchEntityDao: SearchEntityDao) {

    val allSearches = searchEntityDao.getAll()

    suspend fun insert(entity: SearchEntity): Long {
        return searchEntityDao.insert(entity)
    }
}