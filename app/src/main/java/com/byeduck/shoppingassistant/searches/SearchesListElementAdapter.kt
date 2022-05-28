package com.byeduck.shoppingassistant.searches

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.byeduck.shoppingassistant.LiveRecyclerViewAdapter
import com.byeduck.shoppingassistant.R
import com.byeduck.shoppingassistant.databinding.ListelemSearchBinding
import com.byeduck.shoppingassistant.db.SearchEntity
import com.byeduck.shoppingassistant.dialogs.SearchActionsDialog
import java.time.format.DateTimeFormatter

class SearchesListElementAdapter(
    val context: Context,
    private val fragmentManager: FragmentManager,
    searchViewModel: SearchViewModel,
    lifecycleOwner: LifecycleOwner
) : LiveRecyclerViewAdapter<SearchEntity, SearchesListElementAdapter.SearchesListViewHolder>(
    SearchesMergeHandler(), searchViewModel.allSearches, lifecycleOwner
) {

    inner class SearchesListViewHolder(val binding: ListelemSearchBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun doCreateViewHolder(parent: ViewGroup, viewType: Int): SearchesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListelemSearchBinding.inflate(inflater, parent, false)
        return SearchesListViewHolder(binding)
    }

    override fun doBindViewHolder(
        holder: SearchesListViewHolder,
        position: Int,
        current: SearchEntity
    ) {
        holder.binding.root.setOnLongClickListener {
            val dialog = SearchActionsDialog()
            dialog.show(fragmentManager, "search_actions")
            true
        }
        holder.binding.searchIdLabel.text = current.id.toString()
        holder.binding.searchDateLabel.text = current.date.format(
            DateTimeFormatter.ofPattern(context.getString(R.string.date_fromat))
        )
        holder.binding.searchQueryLabel.text = current.query
        holder.binding.searchCategoryLabel.text = current.category
    }
}