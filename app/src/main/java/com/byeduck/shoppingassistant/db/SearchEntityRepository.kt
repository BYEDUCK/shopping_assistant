package com.byeduck.shoppingassistant.db

import com.byeduck.shoppingassistant.db.dao.SearchEntityDao

class SearchEntityRepository(private val searchEntityDao: SearchEntityDao) {

    val allSearches = searchEntityDao.getAll()

    fun insert(entity: SearchEntity) {
        searchEntityDao.insert(entity)
    }
}