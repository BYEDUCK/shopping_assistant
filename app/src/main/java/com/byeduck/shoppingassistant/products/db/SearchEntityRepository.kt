package com.byeduck.shoppingassistant.products.db

import com.byeduck.shoppingassistant.products.db.dao.SearchEntityDao

class SearchEntityRepository(private val searchEntityDao: SearchEntityDao) {

    suspend fun insert(entity: SearchEntity): Long {
        return searchEntityDao.insert(entity)
    }
}