package com.byeduck.shoppingassistant.searches

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.byeduck.shoppingassistant.R
import com.byeduck.shoppingassistant.databinding.ListelemSearchBinding
import com.byeduck.shoppingassistant.db.SearchEntity
import com.byeduck.shoppingassistant.dialogs.SearchActionsDialog
import java.time.format.DateTimeFormatter

class SearchesListElementAdapter(
    val context: Context,
    private val fragmentManager: FragmentManager
) : ListAdapter<SearchEntity, SearchesListElementAdapter.SearchesListViewHolder>(
    SearchesDiffCallback()
) {

    inner class SearchesListViewHolder(val binding: ListelemSearchBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListelemSearchBinding.inflate(inflater, parent, false)
        return SearchesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchesListViewHolder, position: Int) {
        val current = getItem(position)
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

    private class SearchesDiffCallback : DiffUtil.ItemCallback<SearchEntity>() {

        override fun areItemsTheSame(oldItem: SearchEntity, newItem: SearchEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchEntity, newItem: SearchEntity): Boolean {
            return oldItem == newItem
        }

    }
}