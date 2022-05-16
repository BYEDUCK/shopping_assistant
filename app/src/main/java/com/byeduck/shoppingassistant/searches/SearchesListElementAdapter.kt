package com.byeduck.shoppingassistant.searches

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.byeduck.shoppingassistant.R
import com.byeduck.shoppingassistant.databinding.ListelemSearchBinding
import com.byeduck.shoppingassistant.db.SearchEntity
import java.time.format.DateTimeFormatter

class SearchesListElementAdapter(
    val context: Context,
    searchViewModel: SearchViewModel,
    lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<SearchesListElementAdapter.SearchesListViewHolder>() {

    private var searches: MutableList<SearchEntity> = ArrayList()
    private val mergeHandler = SearchesMergeHandler()

    init {
        searchViewModel.allSearches.observe(lifecycleOwner) { entities ->
            val changeList = mergeHandler.handleMerge(searches, entities)
            changeList.toBeModified.forEach { (position, modification) ->
                run {
                    searches[position] = modification
                    notifyItemChanged(position)
                }
            }
            changeList.toBeAdded.forEach {
                searches.add(it)
                notifyItemInserted(searches.size - 1)
            }
            changeList.toBeRemoved.forEach {
                searches.removeAt(it)
                notifyItemRemoved(it)
            }
        }
    }

    inner class SearchesListViewHolder(val binding: ListelemSearchBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListelemSearchBinding.inflate(inflater, parent, false)
        return SearchesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchesListViewHolder, position: Int) {
        val current = searches[position]
        holder.binding.searchIdLabel.text = current.id.toString()
        holder.binding.searchDateLabel.text = current.date.format(
            DateTimeFormatter.ofPattern(context.getString(R.string.date_fromat))
        )
        holder.binding.searchQueryLabel.text = current.query
        holder.binding.searchCategoryLabel.text = current.category
    }

    override fun getItemCount(): Int = searches.size
}