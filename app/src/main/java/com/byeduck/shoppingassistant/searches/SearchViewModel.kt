package com.byeduck.shoppingassistant.searches

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.byeduck.shoppingassistant.db.AppDb
import com.byeduck.shoppingassistant.db.SearchEntity
import com.byeduck.shoppingassistant.db.SearchEntityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SearchEntityRepository
    val allSearches: LiveData<List<SearchEntity>>

    init {
        val dao = AppDb.getDatabase(application).searchEntityDao()
        repository = SearchEntityRepository(dao)
        allSearches = repository.allSearches
    }

    suspend fun addShoppingList(entity: SearchEntity) {
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            repository.insert(entity)
        }
    }
}