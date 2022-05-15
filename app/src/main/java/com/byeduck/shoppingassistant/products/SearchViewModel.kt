package com.byeduck.shoppingassistant.products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.byeduck.shoppingassistant.products.db.AppDb
import com.byeduck.shoppingassistant.products.db.SearchEntity
import com.byeduck.shoppingassistant.products.db.SearchEntityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SearchEntityRepository

    init {
        val dao = AppDb.getDatabase(application).searchEntityDao()
        repository = SearchEntityRepository(dao)
    }

    suspend fun addShoppingList(entity: SearchEntity): Long {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            repository.insert(entity)
        }
    }
}