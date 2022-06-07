package com.byeduck.shoppingassistant.searches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DividerItemDecoration
import com.byeduck.shoppingassistant.databinding.FragmentSearchListBinding

class SearchListFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchListBinding.inflate(inflater, container, false)
        val activity = requireActivity()
        searchViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(activity.application)
        )[SearchViewModel::class.java]
        val recyclerViewAdapter =
            SearchesListElementAdapter(activity.applicationContext)
        binding.searchesListRecyclerView.adapter = recyclerViewAdapter
        binding.searchesListRecyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        searchViewModel.allSearches.observe(activity) {
            if (!it.isNullOrEmpty()) {
                recyclerViewAdapter.submitList(it)
            }
        }
        val recyclerView = binding.searchesListRecyclerView
        val selectionTracker = SelectionTracker.Builder(
            SearchListFragment::class.java.name,
            recyclerView,
            StableIdKeyProvider(recyclerView),
            SearchesDetailsLookup(recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        recyclerViewAdapter.tracker = selectionTracker
        return binding.root
    }
}