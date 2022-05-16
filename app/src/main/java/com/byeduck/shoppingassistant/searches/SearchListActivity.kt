package com.byeduck.shoppingassistant.searches

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.byeduck.shoppingassistant.databinding.ActivitySearchListBinding

class SearchListActivity : AppCompatActivity() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySearchListBinding.inflate(layoutInflater)
        searchViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[SearchViewModel::class.java]
        val recyclerViewAdapter =
            SearchesListElementAdapter(applicationContext, searchViewModel, this)
        binding.searchesListRecyclerView.adapter = recyclerViewAdapter
        binding.searchesListRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.searchesListRecyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        setContentView(binding.root)
    }
}